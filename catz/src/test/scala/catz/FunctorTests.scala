package catz

class FunctorTests extends CatzSuite {

  test("FunctorEx1 tests") {
    FunctorEx1.listOption1 shouldBe List(Some(1), None, Some(2))
    FunctorEx1.listOption2 shouldBe List(Some(2), None, Some(3))
  }

  test("FunctorEx2 tests") {
    import cats.data.Nested
    FunctorEx2.nested1 shouldBe Nested(List(Some(1), None, Some(2)))
    FunctorEx2.nested2 shouldBe Nested(List(Some(2), None, Some(3)))
  }

}