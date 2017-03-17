package catz

class MonadTests extends CatzSuite {

  test("MonadEx1 tests"){
    MonadEx1.flatten1 shouldBe Some(1)
    MonadEx1.flatten2 shouldBe None
    MonadEx1.flatten3 shouldBe List(1,2,3)
  }

  test("MonadEx2 tests"){
    MonadEx2.ifM shouldBe List(1,2,3,4,1,2)
  }

}
