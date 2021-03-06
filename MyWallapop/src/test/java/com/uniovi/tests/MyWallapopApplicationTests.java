package com.uniovi.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
//import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.uniovi.tests.pageobjects.PO_GestionarOfertasView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_UserListView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@SpringBootTest
public class MyWallapopApplicationTests {
	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox65\\firefox.exe";
	static String Geckdriver024 = "C:\\geckodriver024win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	private void navigateUrl(String URL, String pag) {
		driver.navigate().to(URL + pag);
		// new WebDriverWait(driver, 2);
	}

	// Antes de cada prueba se navega al URL home de la aplicación
	@BeforeEach
	public void setUp() {
		navigateUrl(URL, "");
	}

	// Después de cada prueba se borran las cookies del navegador
	@AfterEach
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {

	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/**
		 * 
		 */

	// -----------------------PRUEBAS-------------------
	@Test
	public void Prueba00Conexion() {

		System.out.println(driver.getCurrentUrl());
		System.out.println();
		WebElement prueba = driver.findElement(By.id("prueba"));
		System.out.println("-------------------");
		System.out.println(prueba.getText());
		assertEquals(prueba.getText(), "Wallapop App");

//	WebElement we = driver.getTitle();

	}

//	@Test
//	public void Prueba00RegistrosInvalidos() {
//		// Vamos al formulario de registro
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "99999990A@wywallapop.com", "Pedro", "Díaz", "123456", "123456");
//		PO_View.getP();
//		// COmprobamos el error de email repetido.
//		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "abc@def.gg", "aaa", "abcdef", "123456", "123456");
//		// COmprobamos el error de Nombre corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "abc@def.gg", "abcdef", "sss", "123456", "123456");
//		// COmprobamos el error de Apellido corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "abc@def.gg", "Pedro", "Pedro", "12", "12");
//		// COmprobamos el error de contrasenya corta corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "abc@def.gg", "Pedro", "Pedro", "111111", "222222");
//		// COmprobamos el error de paswordConfirm diferente a pasword .
//		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
//	}

//----------------OBLIGATORIOS------------------

	/**
	 * Registro de Usuario con datos válidos.
	 */
	@Test
	public void Prueba01() {
		String email = "usario@prueba.com";
		String password = "123456";

		PO_RegisterView.registerUser(driver, email, password);
	}

	/**
	 * Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos
	 * vacíos).
	 */
	@Test
	public void Prueba02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "", "", "", "");
		PO_View.getP();
		// COmprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());

	}

	/**
	 * Registro de Usuario con datos inválidos (repetición de contraseña inválida).
	 */
	@Test
	public void Prueba03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "abc@def.gg", "Pedro", "Pedro", "111111", "222222");
		PO_View.getP();
		// COmprobamos el error de paswordConfirm diferente a pasword .
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	/**
	 * Registro de Usuario con datos inválidos (email existente).
	 */
	@Test
	public void Prueba04() {

		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990A@wywallapop.com", "Pedro", "Díaz", "123456", "123456");
		PO_View.getP();
		// COmprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());

	}

	/**
	 * Inicio de sesión con datos válidos (administrador).
	 */
	@Test
	public void Prueba05() {
		PO_PrivateView.login(driver, "admin@email.com", "admin", "Esta es la parte privada de la web");

	}

	/**
	 * Inicio de sesión con datos válidos (usuario estándar).
	 */
	@Test
	public void Prueba06() {
		PO_PrivateView.login(driver, "pablo@wywallapop.com", "123456", "Esta es la parte privada de la web");
	}

	/**
	 * Inicio de sesión con datos inválidos (usuario estándar, campo email y
	 * contraseña vacíos).
	 * 
	 */
	@Test
	public void Prueba07() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");
		// COmprobamos que da error de entrada
		PO_View.getP();
		// COmprobamos el error mirando que estemos en la misma pagina
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());

	}

	/**
	 * Inicio de sesión con datos válidos (usuario estándar, email existente, pero
	 * contraseña incorrecta).
	 */
	@Test
	public void Prueba08() {

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "user@wywallapop.com", "1");
		// COmprobamos que da error de entrada
		PO_View.getP();
		// COmprobamos el error mirando que estemos en la misma pagina
		PO_RegisterView.checkKey(driver, "Error.login.email.error", PO_Properties.getSPANISH());

	}

	/**
	 * Inicio de sesión con datos inválidos (usuario estándar, email no existente en
	 * la aplicación).
	 */
	@Test
	public void Prueba09() {

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "inexistenteUsuario@wywallapop.com", "123321");
		// COmprobamos que da error de entrada
		PO_View.getP();
		// COmprobamos el error mirando que estemos en la misma pagina
		PO_RegisterView.checkKey(driver, "Error.login.email.error", PO_Properties.getSPANISH());

	}

	/**
	 * Hacer click en la opción de salir de sesión y comprobar que se redirige a la
	 * página de inicio de sesión (Login).
	 */
	@Test
	public void Prueba10() {
		PO_PrivateView.login(driver, "pablo@wywallapop.com", "123456", "Esta es la parte privada de la web");
		// desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// comprobamos estar desconectados
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}// TODO [Prueba11]

	/**
	 * Comprobar que el botón cerrar sesión no está visible si el usuario no está
	 * autenticado.
	 */
	@Test
	public void Prueba11() {

		int locale = PO_Properties.getSPANISH();
		PO_RegisterView.checkKey(driver, "welcome.message", locale);

		String text = PO_View.getP().getString("logout.message", locale);
		String busqueda = "//*[contains(text(),'" + text + "')]";

		Boolean resultado;

		resultado = (new WebDriverWait(driver, 2))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(busqueda)));

		assertTrue(resultado);

	}

	/**
	 * Mostrar el listado de usuarios y comprobar que se muestran todos los que
	 * existen en el sistema.
	 */
	@Test
	public void Prueba12() {

		PO_UserListView.accesoUserList(driver);

		PO_UserListView.checkNumberOfUsersOnList(driver, 9);

	}

	/**
	 * Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar
	 * que la lista se actualiza y que el usuario desaparece.
	 */
	@Test
	public void Prueba13() {
		PO_UserListView.accesoUserList(driver);

		PO_UserListView.deleteUser(driver, 0);

		PO_UserListView.checkNumberOfUsersOnList(driver, 8);

	}

	/**
	 * Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar
	 * que la lista se actualiza y que el usuario desaparece.
	 * 
	 */
	@Test
	public void Prueba14() {

		PO_UserListView.accesoUserList(driver);

		PO_UserListView.deleteUser(driver, 7);

		PO_UserListView.checkNumberOfUsersOnList(driver, 7);

	}

	/**
	 * Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se
	 * actualiza y que los usuarios desaparecen.
	 */
	@Test
	public void Prueba15() {

		PO_UserListView.accesoUserList(driver);

		PO_UserListView.deleteUser(driver, 0, 1, 2);

		PO_UserListView.checkNumberOfUsersOnList(driver, 4);

	}

	/**
	 * Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar el
	 * botón Submit. Comprobar que la oferta sale en el listado de ofertas de dicho
	 * usuario.
	 */
	@Test
	public void Prueba16() {

		String email = "99999988F@wywallapop.com";
		String password = "123456";
		// registro un usuario porque lo borramos en pruebas anteriores
//		PO_RegisterView.registerUser(driver, email, password);
		// retorno a home
		driver.manage().deleteAllCookies();
		navigateUrl(URL, "");

		// entro en la vista de la lista de ofertas
		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/ownList");
		// recupero los elementos en la lista antes del add
		int elemeBeforeAdd = PO_GestionarOfertasView.checkNumberOfOffersOnList(driver, 0);
		// retorno a home
		driver.manage().deleteAllCookies();
		navigateUrl(URL, "");
		// accedo al usuario y a sus ofertas add
		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/add");
		// relleno el formulario
		PO_GestionarOfertasView.fillAddOfferForm(driver, email, UUID.randomUUID().toString(),
				"Oferta con una descripcion de mas de veinte caracteres......", 10 + "");
		// compruebo cuantos elementos hay ahora
		PO_GestionarOfertasView.checkNumberOfOffersOnList(driver, elemeBeforeAdd + 1);

	}

	/**
	 * Ir al formulario de alta de oferta, rellenarla con datos inválidos (campo
	 * título vacío) y pulsar el botón Submit. Comprobar que se muestra el mensaje
	 * de campo obligatorio.
	 * 
	 */
	@Test
	public void Prueba17() {

		String email = "99999988F@wywallapop.com";
		String password = "123456";
		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/add");
		// relleno el formulario
		PO_GestionarOfertasView.fillAddOfferForm(driver, email, "", "Oferta", 10 + "");
		// COmprobamos que da error de entrada
		PO_View.getP();
		// COmprobamos el error mirando que estemos en la misma pagina
		SeleniumUtils.EsperaCargaPagina(driver, "free", "//*[@id=\"offer-add\"]", PO_View.getTimeout());

	}

	/**
	 * Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran
	 * todas los que existen para este usuario.
	 */
	@Test
	public void Prueba18() {
		// accedo al usuario y a sus ofertas list
		PO_GestionarOfertasView.accesoGestionOfertas(driver, "99999988F@wywallapop.com", "123456", "offer/list");
		PO_GestionarOfertasView.checkNumberOfOffersOnList(driver, 4);

	}

	/**
	 * Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que
	 * la lista se actualiza y que la oferta desaparece.
	 */
	@Test
	public void Prueba19() {

		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.insertOffersTo(driver, URL, email, password, 3);

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/ownList");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPaginaxpath(driver, "//*[@id=\"31\"]",
				PO_View.getTimeout());
		elementos.get(0).click();
		assertTrue((new WebDriverWait(driver, PO_View.getTimeout()))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"31\"]"))));

	}

	/**
	 * Ir a la lista de ofertas, borrar la última oferta de la lista, comprobar que
	 * la lista se actualiza y que la oferta desaparece.
	 */
	@Test
	public void Prueba20() {

		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/ownList");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPaginaxpath(driver, "//*[@id=\"34\"]",
				PO_View.getTimeout());
		elementos.get(0).click();
		assertTrue((new WebDriverWait(driver, PO_View.getTimeout()))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"34\"]"))));
	}

	/**
	 * Hacer una búsqueda con el campo vacío y comprobar que se muestra la página
	 * que corresponde con el listado de las ofertas existentes en el sistema
	 */
	@Test
	public void Prueba21() {
		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/list");

		PO_GestionarOfertasView.busquedaOferta(driver, "");

	}

	/**
	 * Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar
	 * que se muestra la página que corresponde, con la lista de ofertas vacía.
	 */
	@Test
	public void Prueba22() {
		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/list");

		PO_GestionarOfertasView.busquedaOferta(driver, "x");
		assertTrue((new WebDriverWait(driver, PO_View.getTimeout()))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tbody/tr"))));

	}

	// 
	/**
	 * Sobre una búsqueda determinada (a elección del desarrollador), comprar una
	 * oferta que deja un saldo positivo en el contador del comprador. Comprobar que
	 * el contador se actualiza correctamente en la vista del comprador.
	 */
	@Test
	public void Prueba23() {
		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/list");// *[@id="25"]

		PO_GestionarOfertasView.busquedaOferta(driver, "Oferta19");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPaginaxpath(driver, "//*[@id=\"Oferta19\"]", // xpath=
																											// //*[@id=\"23\"]
				PO_View.getTimeout());
		elementos.get(0).click();

//		PO_RegisterView.checkElement(driver, "text", "99999988F@wywallapop.com. [80.0 €]");

	}

	/**
	 * Sobre una búsqueda determinada (a elección del desarrollador), comprar una
	 * oferta que deja un saldo 0 en el contador del comprador. Comprobar que el
	 * contador se actualiza correctamente en la vista del comprador.
	 */
	@Test
	public void Prueba24() {

		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/list");

		PO_GestionarOfertasView.busquedaOferta(driver, "Oferta18");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPaginaxpath(driver, "//*[@id=\"Oferta18\"]",
				PO_View.getTimeout());
		elementos.get(0).click();

	}

	/**
	 * Sobre una búsqueda determinada (a elección del desarrollador), intentar
	 * comprar una oferta que esté por encima de saldo disponible del comprador. Y
	 * comprobar que se muestra el mensaje de saldo no suficiente.
	 */
	@Test
	public void Prueba25() {
		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/list");

		PO_GestionarOfertasView.busquedaOferta(driver, "Oferta20");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPaginaxpath(driver, "//*[@id=\"Oferta20\"]",
				PO_View.getTimeout());
		elementos.get(0).click();

		PO_RegisterView.checkKey(driver, "cantBuy.message", PO_Properties.getSPANISH());

	}

	/**
	 * Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar
	 * que aparecen las ofertas que deben aparecer.
	 */
	@Test
	public void Prueba26() {
		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/buyView");
		// compruebo que se hayan realizado 2 compras
		PO_GestionarOfertasView.checkNumberOfOffersOnList(driver, 2);
//		driver.manage().deleteAllCookies();
//		navigateUrl(URL, "");

	}

	/**
	 * Visualizar al menos cuatro páginas haciendo el cambio español/inglés/español
	 * (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	 * Página principal/Opciones principales de usuario/Listado de usuarios /Vista
	 * de alta de oferta.
	 */
	@Test
	public void Prueba27() {

		String lastUUID =PO_GestionarOfertasView.recuperacionDeDatos(driver,URL);

		String email = "99999988F@wywallapop.com";
		String password = "123456";

		PO_GestionarOfertasView.accesoGestionOfertas(driver, email, password, "offer/list");

		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamos a la última página
		elementos.get(3).click();
		SeleniumUtils.EsperaCargaPaginaxpath(driver, "//*[@id=\""+lastUUID+"\"]", PO_View.getTimeout());
	}


	/**
	 * Intentar acceder sin estar autenticado a la opción de listado de usuarios del
	 * administrador. Se deberá volver al formulario de login.
	 */
	@Test
	public void Prueba28() {
		navigateUrl(URL, "/user/list");
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}

	/**
	 * Intentar acceder sin estar autenticado a la opción de listado de ofertas
	 * propias de un usuario estándar. Se deberá volver al formulario de login.
	 */
	@Test
	public void Prueba29() {
		navigateUrl(URL, "/offer/list");
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
	}
	/**
	 * Estando autenticado como usuario estándar intentar acceder a la opción de
	 * listado de usuarios del administrador. Se deberá indicar un mensaje de acción
	 * prohibida.
	 */
	@Test
	public void Prueba30() {
		PO_PrivateView.login(driver, "pablo@wywallapop.com", "123456", "Esta es la parte privada de la web");
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		navigateUrl(URL, "/user/list");
		new WebDriverWait(driver, PO_View.getTimeout())
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"user-list\"]")));

	}

}