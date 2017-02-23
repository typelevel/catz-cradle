package catz

class SemigroupTests extends CatzSuite {

  test("SemigroupEx1 tests") {
    SemigroupEx1.combine1 shouldBe 3
    SemigroupEx1.combine2 shouldBe 6
    SemigroupEx1.combine3 shouldBe 6
  }

  test("SemigroupEx2 tests") {
    SemigroupEx2.combine1 shouldBe 3
  }

  test("SemigroupEx3 tests") {
    SemigroupEx3.combineMap1 shouldBe Map("hello" -> 2, "cats" -> 3, "world" -> 1)
    SemigroupEx3.combineMap2 shouldBe Map("hello" -> 2, "cats" -> 3, "world" -> 1)
  }

  test("SemigroupEx4 tests") {
    SemigroupEx4.x shouldBe Map('b' -> 5, 'c' -> 4, 'a' -> 1)
    SemigroupEx4.y shouldBe Map(2 -> List("cats"), 1 -> List("hello", "world"))
    SemigroupEx4.xb shouldBe true
    SemigroupEx4.yb shouldBe true
  }

  test("SemigroupEx5 tests") {
    SemigroupEx5.leftwards shouldBe 6
    SemigroupEx5.rightwards shouldBe 6
    SemigroupEx5.sumLeft shouldBe 3
    SemigroupEx5.sumRight shouldBe 12
    SemigroupEx5.result shouldBe 15
  }
}