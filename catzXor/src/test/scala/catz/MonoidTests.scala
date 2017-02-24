package catz

class MonoidTests extends CatzSuite {

  test("MonoidEx1 tests") {
    // Associativity and Semigroup with Empty
    MonoidEx1.combine1 shouldBe 1
    MonoidEx1.combine2 shouldBe 1
  }

  test("MonoidEx2 tests") {
    // combineAll functionality as have an Empty
    MonoidEx2.combineAll1 shouldBe 6
    MonoidEx2.combineAll2 shouldBe "hello world"
    MonoidEx2.combineAll3 shouldBe Map('b' -> 7, 'c' -> 5, 'a' -> 3)
    MonoidEx2.combineAll4 shouldBe Set(5,1,2,3,4)
  }

  test("MonoidEx3 tests") {
    import cats.data.NonEmptyList
    MonoidEx3.optionMonoid1 shouldBe Some(NonEmptyList.of(1,2,3,4,5,6))
    MonoidEx3.optionMonoid2 shouldBe None
    MonoidEx3.optionMonoid3 shouldBe Some(NonEmptyList.of(1,2,3,4,5,6))
  }

}
