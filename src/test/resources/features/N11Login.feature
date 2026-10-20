@external @login
Feature: N11 login

  Scenario: Kullanıcı geçerli bilgilerle giriş yapabilmelidir
    Given Kullanıcı Anasayfaya gider.
    When Ana sayfanın yüklendiği doğrulanır.
    And Kullanıcı ana sayfada Giriş Yap butonunu bulur ve tıklar.
    And Giriş sayfasının yüklendiği doğrulanır.
    And Kullanıcı giriş sayfasında geçerli bir kullanıcı adı girer.
    And Kullanıcı giriş sayfasında geçerli bir şifre girer.
    And Kullanıcı "Giriş Yap" butonuna tıklar.
    Then Kullanıcının hesabına başarıyla giriş yapıldığı doğrulanır.
