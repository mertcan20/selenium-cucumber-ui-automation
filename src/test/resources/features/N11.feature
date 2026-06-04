@smoke
Feature: N11 Login Functionality

  Background:
    Given Kullanıcı Anasayfaya gider.
    When Ana sayfanın yüklendiği doğrulanır.

  @login
  Scenario: Kullanıcı geçerli bilgilerle giriş yapabilmelidir
    And Kullanıcı ana sayfada Giriş Yap butonunu bulur ve tıklar.
    And Giriş sayfasının yüklendiği doğrulanır.
    And Kullanıcı giriş sayfasında geçerli bir kullanıcı adı girer.
    And Kullanıcı giriş sayfasında geçerli bir şifre girer.
    And Kullanıcı "Giriş Yap" butonuna tıklar.
    Then Kullanıcının hesabına başarıyla giriş yapıldığı doğrulanır.

  @urunEkleme
  Scenario: Kullanıcı ürün ekleme sayfasına erişebilir
    And Kullanıcı ana sayfada Giriş Yap butonunu bulur ve tıklar.