package catz

class SemigroupTests extends CatzSuite {

  test("SemigroupEx1 tests") {
    SemigroupEx1.combine1 shouldBe 3
    SemigroupEx1.combine2 shouldBe 6
    SemigroupEx1.combine3 shouldBe 6
  }
}