package com.thoughtworks.DDF.Option

import com.thoughtworks.DDF.Arrow.Arr

trait Option[Info[_], Repr[_]] extends OptionInfo[Info, Repr] with Arr[Info, Repr] {
  def none[A](implicit ai: Info[A]): Repr[scala.Option[A]]

  def some[A](implicit ai: Info[A]): Repr[A => scala.Option[A]]

  final def some_[A]: Repr[A] => Repr[scala.Option[A]] = a => app(some(reprInfo(a)))(a)

  def optionMatch[A, B](implicit ai: Info[A], bi: Info[B]): Repr[scala.Option[A] => B => (A => B) => B]

  final def optionMatch_[A, B](oa: Repr[scala.Option[A]])(implicit bi: Info[B]): Repr[B => (A => B) => B] =
    app(optionMatch(optionElmInfo(reprInfo(oa)), bi))(oa)

  final def optionMatch__[A, B]: Repr[scala.Option[A]] => Repr[B] => Repr[(A => B) => B] = oa => b =>
    app(optionMatch_(oa)(reprInfo(b)))(b)

  final def optionMatch___[A, B]: Repr[scala.Option[A]] => Repr[B] => Repr[A => B] => Repr[B] = oa => b =>
    app(optionMatch__(oa)(b))
}
