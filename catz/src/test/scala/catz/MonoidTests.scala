package catz

class MonoidTests extends CatzSuite {

  test("MonoidEx1 tests") {
    // Associativity
    MonoidEx1.combine1 shouldBe 1
    MonoidEx1.combine2 shouldBe 1
  }

}
