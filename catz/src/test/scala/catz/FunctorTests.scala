package catz

class FunctorTests extends CatzSuite {

  test("FunctorEx1 tests") {
    FunctorEx1.listOption1 shouldBe List(Some(1), None, Some(2))
    FunctorEx1.listOption2 shouldBe List(Some(2), None, Some(3))
  }
}