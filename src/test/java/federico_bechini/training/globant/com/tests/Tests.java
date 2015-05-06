package federico_bechini.training.globant.com.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import federico_bechini.training.globant.com.pages.HomePage;

public class Tests {

		WebDriver driver;

		@BeforeMethod
		public void before() {
			
			/* Chrome */
			//System.setProperty("webdriver.chrome.driver","D:/Soft de la facultad/chromeDriver/chromedriver.exe");
			//driver = new ChromeDriver();
			
			/* Firefox */
			driver = new FirefoxDriver();
			
			/* IExplorer*/
			//System.setProperty("webdriver.ie.driver","D:/Soft de la facultad/InternetExplorerDriver/IEDriverServer.exe");
			//driver = new InternetExplorerDriver();
		}
		
		@AfterMethod
		public void after(){
			driver.quit();
		}
		
		
		@Test(enabled=true, description = "Valida que la pagina sea la esperada en base a su titulo.")
		public void validatePage() throws InterruptedException {
			
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
				Reporter.log("<br>Validando pagina por su titulo ...");
			homePage.valid("Películas y Series en versión original");	
		}
		
		@Test(enabled=true, description = "Mostrar la cantidad de resultados por consola (con 3 o mas letras)")
		public void searchResults() throws InterruptedException {
			
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
				Reporter.log("<br>Buscando una palabra en el buscador.");
			homePage.search("Interstellar");
			
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			homePage.pause(3);
				Reporter.log("<br>Buscando cantidad resultados.");
			WebElement  result = driver.findElements(By.tagName("span")).get(1).findElement(By.tagName("b"));

			int cantidad = -1;
			if(result != null) cantidad = Integer.valueOf(result.getText());
			System.out.println("La Cantidad de Resultados encontrados: "+cantidad);
				Reporter.log("<br>La Cantidad de Resultados encontrados: "+cantidad);
				
			Assert.assertTrue(cantidad >= 0, "ERROR: occurio algun problema inesperado.");
		}
		
		@Test(enabled=true, description = "Test: Validar formularion registracion no fue valido")
		public void registrationFailed() {
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
			
			homePage.pause(5);
			WebElement linkFirstMovie = driver.findElements(By.tagName("table")).get(8).findElement(By.tagName("a"));
			
				Reporter.log("<br>Entrado a la primera pelicula del home.");
			linkFirstMovie.click();
			
			//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			homePage.pause(15);
			//WebElement  registrationLink = driver.findElement(By.cssSelector("a[href*='http://www.mejorenvo.com/secciones.php?sec=tu_cuenta&func=registro']"));
			WebElement  registrationLink = driver.findElement(By.linkText("registrarte"));
				Reporter.log("<br>Entrado a la seccion resgistrar.");
			registrationLink.click();
			
			homePage.pause(10);
				Reporter.log("<br>Completando solo un campo del formulario.");
			WebElement  nombreForm = driver.findElement(By.cssSelector("input[name='datos[user]']"));
			nombreForm.sendKeys("Federico"); 
			
				Reporter.log("<br>Enviando el formulario para verificar la creacion de la cuenta.");
			WebElement  submitForm = driver.findElement(By.cssSelector("input[type='button'][name='enviar']"));
			submitForm.click();
			
			homePage.pause(10);
			WebDriverWait wait = new WebDriverWait(driver, 10);		//espero un tiempo para detectar el alert.
			Alert alert = driver.switchTo().alert();
			
			int flagAlert = 1;
			 if(wait.until(ExpectedConditions.alertIsPresent())!=null){
				 System.out.println("Alert was present");
				 	Reporter.log("<br>Alert was present");
				 System.out.println("ALERT:"+alert.getText());
				 	Reporter.log("<br>ALERT:"+alert.getText());
				 flagAlert = 0;
			 }
			 else{
				 System.out.println("Alert was not present");
				 	Reporter.log("<br>Alert was not present");
			 }
			 
			 Assert.assertTrue(flagAlert == 0  ,"ERROR: Alert was not present.");
		}
		
		@Test(enabled=true, description = "Test: Validar formulario registracion fue valido.")
		public void registrationAccepted() {
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
			
			homePage.pause(5);
			WebElement linkFirstMovie = driver.findElements(By.tagName("table")).get(8).findElement(By.tagName("a"));

				Reporter.log("<br>Entrado a la primera pelicula del home.");
			linkFirstMovie.click();
			
			//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			homePage.pause(10);		
			//WebElement  registrationLink = driver.findElement(By.cssSelector("a[href*='http://www.mejorenvo.com/secciones.php?sec=tu_cuenta&func=registro']"));
			WebElement  registrationLink = driver.findElement(By.linkText("registrarte"));
				Reporter.log("<br>Entrado a la seccion resgistrar.");
				
			registrationLink.click();
			
			
			homePage.pause(15);		
			WebElement  userForm = driver.findElement(By.cssSelector("input[name='datos[user]']"));
			WebElement  passForm = driver.findElement(By.cssSelector("input[name='datos[pass]']"));
			WebElement  emailForm = driver.findElement(By.cssSelector("input[name='datos[email]']"));
			WebElement  nameForm = driver.findElement(By.cssSelector("input[id='nombre']"));
			WebElement  lastnameForm = driver.findElement(By.cssSelector("input[id='apellido']"));
			WebElement  movilForm = driver.findElement(By.cssSelector("input[id='movil']"));
			WebElement  cpForm = driver.findElement(By.cssSelector("input[id='cp']"));
			
				Reporter.log("<br>Completando datos del formulario.");
			
			Select paisForm = new Select(driver.findElement(By.cssSelector("select[id='pais']")));
			Select dayForm = new Select(driver.findElement(By.cssSelector("select[id='spe_dia_nacimiento']")));
			Select monthForm = new Select(driver.findElement(By.cssSelector("select[id='spe_mes_nacimiento']")));
			Select yearForm = new Select(driver.findElement(By.cssSelector("select[id='fec_ncto']")));
			Select sexForm = new Select(driver.findElement(By.cssSelector("select[id='sexo']")));
			
			userForm.sendKeys("Federic4828");  //Tener en cuenta de cambiar cada vez que se hace el test
			passForm.sendKeys("123456789");
			emailForm.sendKeys("hol5872a@hotmail.com");
			nameForm.sendKeys("Federico");
			lastnameForm.sendKeys("Bech");
			paisForm.selectByVisibleText("Chile");
			movilForm.sendKeys("3511231231");
			dayForm.selectByVisibleText("10");
			monthForm.selectByVisibleText("Abril");
			yearForm.selectByVisibleText("1989");
			sexForm.selectByVisibleText("Hombre");
			cpForm.sendKeys("5000");
			
			WebElement  submitForm = driver.findElement(By.cssSelector("input[type='button'][name='enviar']"));
				Reporter.log("<br>Enviando el formulario para verificar la creacion de la cuenta.");
			submitForm.click();
			
			homePage.pause(13);
				Reporter.log("<br>Buscando el texto que nos informa que todo salio bien.");
			WebElement bodyText = driver.findElement(By.tagName("body"));
			String textBody = bodyText.getText();
			
			boolean tmp = textBody.contains("Se ha insertado el usuario en la DB del foro correctamente");
			boolean tmpBad = textBody.contains("El nombre de usuario proporcionado ya existe en nuestra base de datos.");
			if(tmp){
				Assert.assertTrue(true);
			}
			else if (tmpBad){
				Assert.assertFalse(true,"ERROR: El usuario ya se encuentra registrado intente otro");
			}else{
				Assert.assertFalse(true,"ERROR: hay algun problema que no permite completar el proceso ...");
			}

				 //Assert.assertTrue(driver.getPageSource().contains("Se ha insertado el usuario en la DB del foro correctamente."),"no se encontro el status");			 
		}

		@Test(enabled=true, description = "Test: Contar cantidad de post en la primera serie del home.")
		public void counterComments() {
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
				
			homePage.pause(10);	
			//WebElement linkFirstTvShow = driver.findElements(By.tagName("table")).get(6);
			WebElement linkFirstTvShow = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td/table[1]/tbody/tr/td/table/tbody/tr/td[2]/a[1]"));
			
				Reporter.log("<br>Entrado a la primera serie de television del home.");
			linkFirstTvShow.click();
			
			homePage.pause(12);	
			
				Reporter.log("<br>Empezando a contar...");
			WebElement commentsNumber = driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(1) > td:nth-child(2) > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table:nth-child(8) > tbody > tr:nth-child(1) > td:nth-child(2) > span > b"));

			String number = commentsNumber.getText();		//"2 COMENTARIOS"
			number = number.replaceAll("[a-zA-Z]+", ""); //saco las palabras
			number = number.trim();	//saco los espacios
			
			System.out.println("La cantidad de comentarios son: "+number+".");	
				Reporter.log("<br>La cantidad de comentarios son: "+number+".");
				
			Assert.assertTrue(Integer.valueOf(number) >= 0,"ERROR: parece que no existen comentarios ...");
		}
		
		@Test(enabled=true, description = "Test: Verificar que peliculas estan ordenadas por fecha decreciente.")
		public void moviesByDate() {
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
				
			homePage.pause(5);
			//Tabla solo de las peliculas
			WebElement xpathTable = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[1]/table"));
			//[spans i estilo] con las fechas
				Reporter.log("<br>Buscando fechas...");
		    List<WebElement>  dates = xpathTable.findElements(By.cssSelector("span[style*='color:#969696;'] i"));
		    
			System.out.println("Tamaño items peliculas -->" + dates.size());
				Reporter.log("<br>Tamaño items peliculas -->" + dates.size());
			
		    List<Date> dateList = new ArrayList<Date>();
		    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); //formato de la fecha que usare
			   
			for ( WebElement we: dates) {     //recorro cada fecha y saco los espacios y guiones
		        String date = we.getText();		      //2015-05-01 a las 04:55
		        date = date.substring(0, 11);	 	//2015-05-01
		        date = date.replaceAll("-", "");  //20150501
		        
		        Date tmp = null;
				try {
					tmp = dateFormat.parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        dateList.add(tmp);
		    }
			for ( Date we: dateList) {
	        	System.out.println("Fechas del home:"+dateFormat.format(we)); 
			}
		    	Reporter.log("<br>Verificando orden...");
			
		    int i;
		    for(i = 0; i < dateList.size() - 1 && dateList.get(i).compareTo(dateList.get(i+1))>=0? true:false; i++);
		    
		    if(i == dateList.size()-1){
		    	Assert.assertTrue(true);
		    }else{
		    	Reporter.log("<br>ERROR: las peliculas no estan ordenadas forma decreciente");
		    	Assert.assertTrue(false,"ERROR: las peliculas no estan ordenadas forma decreciente.");
		    }
		    //ArrayList<String> Element2 = new ArrayList<String>(Element1);	//creo una copia de la lista
			//Collections.sort(Element2,Collections.reverseOrder());		//ordeno la lista copia
			//comparo si la lista con lista copia son iguales o no.
			//Assert.assertEquals(Element1,Element2,"ERROR: las peliculas no estan ordenadas forma decreciente."); 	
		}
		
		@Test(enabled=true, description = "Test: Determinar si hay publicaciones diarias.")
		public void daylePostVerification() {
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.go(driver);
				Reporter.log("<br>Entrado a mejorenvo.com");
			//WebElement xpathTable = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[1]/table"));
		    //WebElement xpathTable = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table"));

				Reporter.log("<br>Buscando fechas...");
		    List<WebElement>  dates = driver.findElements(By.cssSelector("span[style*='color:#969696;'] i"));
		    
		    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); //formato de la fecha que usare
		    Date todayDate = new Date();
		    System.out.println("Cantidad Items"+dates.size());
		    	Reporter.log("<br>Cantidad Items"+dates.size());
		    List<Date> listOfDates = new ArrayList<Date>();  //lista de fechas
		    
			for ( WebElement we: dates) {    
		        String date = we.getText();		      
		        date = date.substring(0, 11);
		        date = date.replaceAll("-", "");
		       
		        Date tmp = null;
				try {
					tmp = dateFormat.parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					listOfDates.add(tmp);  //agrego las fechas encontrada a a lista Date
		    }
			
			//para testear
			/*
			try {
				listOfDates.add(dateFormat.parse(dateFormat.format(todayDate)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			//Collections.sort(listOfDates);	//ordeno las fechas
			
			for ( Date we: listOfDates) {
	        	System.out.println("Fechas del home:"+dateFormat.format(we)); 
			}
			
			Assert.assertFalse(!listOfDates.contains(dateFormat.format(todayDate)), "ERROR: No contiene la fecha del dia de hoy. No Actualiza Periodicamente");
			
				Reporter.log("<br>Buscando fechas posibles en el rango ...");
			System.out.println("Fecha inicio:"+listOfDates.get(0)+" Fecha final:"+listOfDates.get(listOfDates.size()-1));
			
			//calculo las fechas posibles en el rango
		    List<Date> resultingDates = generateDateListBetween(listOfDates.get(0), listOfDates.get(listOfDates.size()-1));
		    
		
		    //borro las fechas de la listas que ya tengo en lista Date
		    	Reporter.log("<br>Calculando fechas faltantes ...");
		    resultingDates.removeAll(listOfDates);
		    
		    for(Date date : resultingDates){
		        System.out.println("Fechas faltantes:"+dateFormat.format(date));
		    } 
		    
		    Assert.assertFalse(!(resultingDates.size() ==0), "ERROR: No contiene un o mas fechas del rango buscado.");      				
		}
		
		//genera una lista de todas las posibles fechas, entre dos fechas.
		private List<Date> generateDateListBetween(Date startDate, Date endDate){
		   
			List<Date> dates = new ArrayList<Date>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			while (cal.getTime().before(endDate)) {
			    cal.add(Calendar.DATE, 1);
			    dates.add(cal.getTime());
			}
			return dates;
		}
		
}
