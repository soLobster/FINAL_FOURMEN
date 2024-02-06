/**
 * 
 */


document.addEventListener('DOMContentLoaded', function() {

	let idChecked = false;
	let pwdChecked = false;
	let passwordcheckChecked = false;
	let nameChecked = false;
	let nicknameChecked = false;
	let phoneChecked = false;
	let checkidChecked = false;
	let checkphoneChecked =false;
	
	
	document.getElementById("emailcheckbtn").style.pointerEvents = "none";
	let emailinsert = document.querySelector("a#emailinsert");
	let countdownValue = 180; // 초기 초수 설정
	let countdownElement = document.getElementById("countdown");
	let success = document.getElementById("success");
	let countdownInterval;
	let ifemailinsert = document.querySelector('#emailnot');
	let warningmodal = document.querySelector("div#warningmodal");
	let numberwarningmodal = document.querySelector("div#numberwarningmodal");
	let closemodal = document.querySelector("#modal-close");
	let numberclosemodal = document.querySelector("#numbermodal-close");
	let phoneclosemodal = document.querySelector("#phonemodal-close");
	let phonewarningmodal = document.querySelector('div#phonewarningmodal');
	let ifphoneinsert = document.querySelector('#phonenot');
	let phonecountdownElement = document.getElementById("phonecountdown");
	let forgetphonecountdownElement = document.getElementById("forgetphonecountdown");
	let phonesuccess = document.getElementById("phonesuccess");
	let forgetphonesuccess = document.getElementById("forgetphonesuccess");
	let phoneinsert = document.querySelector('a#phoneinsert');
	let forgetphoneinsert = document.querySelector('a#forgetphoneinsert');	
	let phonenumberwarningmodal = document.querySelector("div#phonenumberwarningmodal");
	let phonenumberclosemodal = document.querySelector("#phonenumbermodal-close");
	let forgetphonewarningmodal = document.querySelector("div#forgetphonewarningmodal");
	let forgetphonenumberwrongclose = document.querySelector("#forgetphonenumberwrongclose");
	let forgetcheckphonebox = document.querySelector('div#forgetcheckphonebox');
	let checkmail = document.querySelector('input#checkmail');
	let phonecheck = document.querySelector('input#phonecheck');
	
	checkmail.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >4){
			e.target.value=null;
			return;}	
});

	checkmail.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});
	

phonecheck.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >4){
			e.target.value=null;
			return;}	
});

	phonecheck.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});

	let forgetphonenumberwarningmodal = document.querySelector('div#forgetphonenumberwarningmodal');
		let forgetphonenumberwarningmodalclose = document.querySelector("#forgetphonenumberwarningmodalclose");
	let forgetfullname = document.querySelector('input#forgetfullname');
	
		let forgetfullnamewarningmodal = document.querySelector('div#forgetfullnamewarningmodal');
		let forgetfullnamewarningmodalclose = document.querySelector("#forgetfullnamewarningmodalclose");
	forgetfullnamewarningmodalclose.addEventListener('click', () => {
		forgetfullnamewarningmodal.style.display = 'none';


	});
	
	forgetphonenumberwarningmodalclose.addEventListener('click', () => {
		forgetphonenumberwarningmodal.style.display = 'none';


	});
	
	
	forgetphonenumberwrongclose.addEventListener('click', () => {
		forgetphonewarningmodal.style.display = 'none';


	});
	
	closemodal.addEventListener('click', () => {
		warningmodal.style.display = 'none';


	});
	numberclosemodal.addEventListener('click', () => {
		numberwarningmodal.style.display = 'none';


	});

	phoneclosemodal.addEventListener('click', () => {
		phonewarningmodal.style.display = 'none';
	});

	phonenumberclosemodal.addEventListener('click', () => {
		phonenumberwarningmodal.style.display = 'none';
	});


	phoneinsert.addEventListener('click', () => {
		document.getElementById("phonecheckbtn").style.pointerEvents = "auto";

		countdownValue = 180;
		phonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

		if (countdownInterval) {
			clearInterval(countdownInterval);
			document.getElementById("phonecheckbtn").style.display = 'block';
			countdownInterval = setInterval(function() {
				countdownValue--;
				phonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("phonecheckbtn").style.display = 'none';
					phonecountdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}




		if (!countdownInterval) {
			countdownInterval = setInterval(function() {
				countdownValue--;
				phonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("phonecheckbtn").style.display = 'none';
					phonecountdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}


		getphone();
	});

		

		forgetphoneinsert.addEventListener('click', () => {
		if(forgetfullname.value===''){
			forgetfullnamewarningmodal.style.display = 'block';
			
		} 
		if(forgetfullname.value !=''){
		document.getElementById("forgetphonecheckbtn").style.pointerEvents = "auto";

		countdownValue = 180;
		forgetphonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

		if (countdownInterval) {
			clearInterval(countdownInterval);
			document.getElementById("forgetphonecheckbtn").style.display = 'block';
			countdownInterval = setInterval(function() {
				countdownValue--;
				forgetphonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("forgetphonecheckbtn").style.display = 'none';
					forgetphonecountdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}




		if (!countdownInterval) {
			countdownInterval = setInterval(function() {
				countdownValue--;
				forgetphonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("forgetphonecheckbtn").style.display = 'none';
					forgetphonecountdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}


		getphoneforget();
		}
	});
	
	
	function getphoneforget() {
		let phone = document.querySelector('input#forgetphone').value;
		console.log(phone);

		// 동일한 숫자가 count 이상 반복되는지 확인하는 함수
		function hasRepeatedNumbers(str, count) {
			const regex = new RegExp(`(\\d)\\1{${count - 1},}`, 'g');
			return regex.test(str);
		}

		if ((phone.length >= 12 || phone.length <= 8) || hasRepeatedNumbers(phone, 6)) {
			forgetphonewarningmodal.style.display = 'block';
			document.querySelector('input#forgetphone').value = '';
			
		} else {
			
			forgetcheckphonebox.style.display= 'block';



			const uri = `login/phone/${phone}`;
			console.log(uri);

			axios.get(uri)
				.then((response) => {

					console.log(response.data);
					const forgetphonecheckbtn = document.querySelector("a#forgetphonecheckbtn");

					forgetphonecheckbtn.addEventListener('click', () => {
						const forgetphonecheck = document.querySelector('input#forgetphonecheck').value;
						console.log(forgetphonecheck);
						console.log(response.data);
						if (response.data == forgetphonecheck) {
							console.log("성공");
							forgetphonecheckbtn.style.display = 'none';
							forgetphonecountdownElement.style.display = 'none';
							forgetphonesuccess.style.display = 'block';
							forgetphoneinsert.style.display = 'none';
							document.querySelector('input#forgetphone').setAttribute('readonly', 'readonly');
							document.querySelector('input#forgetphonecheck').setAttribute('readonly', 'readonly');
							document.querySelector('input#forgetfullname').setAttribute('readonly', 'readonly');
							
						} else {
							console.log("인증비번실패");

							forgetphonenumberwarningmodal.style.display = 'block';
							document.querySelector('input#forgetphonecheck').value='';
						}

					});



				})
				.catch((error) => {
					console.log(error);

				});
		}
	}



	emailinsert.addEventListener('click', () => {
		document.getElementById("emailcheckbtn").style.pointerEvents = "auto";

		countdownValue = 180;
		countdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

		if (countdownInterval) {
			clearInterval(countdownInterval);
			document.getElementById("emailcheckbtn").style.display = 'block';
			countdownInterval = setInterval(function() {
				countdownValue--;
				countdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("emailcheckbtn").style.display = 'none';
					countdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}




		if (!countdownInterval) {
			countdownInterval = setInterval(function() {
				countdownValue--;
				countdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("emailcheckbtn").style.display = 'none';
					countdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}

		getAllComments();
		
	});
	

let forgetemailinsert = document.querySelector('a#forgetemailinsert');
let forgetemailcountdown = document.getElementById("forgetemailcountdown");
let forgetemailwarningmodal = document.querySelector('div#forgetemailwarningmodal');
let forgetemailnumberwrongclose = document.querySelector('#forgetemailnumberwrongclose');
let forgetcheckemailbox= document.querySelector('div#forgetcheckemailbox');	
let forgetemailsuccess= document.querySelector('#forgetemailsuccess');
let forgetemailnumberwarningmodal = document.querySelector('div#forgetemailnumberwarningmodal');
let forgetemailnumberwarningmodalclose = document.querySelector('#forgetemailnumberwarningmodalclose');
let forgetpwfullname = document.querySelector('input#forgetpwfullname');
let forgetemailnamewarningmodal = document.querySelector('div#forgetemailnamewarningmodal');
let forgetemailnamewarningmodalclose= document.querySelector('#forgetemailnamewarningmodalclose');
let forgetpwemail =document.querySelector('input#forgetpwemail');
let forgetemailcheck =document.querySelector('input#forgetemailcheck');

forgetpwfullname.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >=10){
			e.target.value=null;
			return;}	
});

forgetpwemail.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >=30){
			e.target.value=null;
			return;}	
});

forgetemailcheck.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >4){
			e.target.value=null;
			return;}	
});

	forgetemailcheck.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});

forgetemailnamewarningmodalclose.addEventListener('click', () => {
		forgetemailnamewarningmodal.style.display = 'none';


	});	
	forgetemailnumberwrongclose.addEventListener('click', () => {
		forgetemailwarningmodal.style.display = 'none';


	});
	
		forgetemailnumberwarningmodalclose.addEventListener('click', () => {
		forgetemailnumberwarningmodal.style.display = 'none';


	});

	
		forgetemailinsert.addEventListener('click', () => {
			if(forgetpwfullname.value===''){
			forgetemailnamewarningmodal.style.display = 'block';
			
		} 
		if(forgetpwfullname.value !=''){
		document.getElementById("forgetemailcheckbtn").style.pointerEvents = "auto";

		countdownValue = 180;
		forgetemailcountdown.textContent = "Please enter the verification number within " + countdownValue + " seconds";

		if (countdownInterval) {
			clearInterval(countdownInterval);
			document.getElementById("forgetemailcheckbtn").style.display = 'block';
			countdownInterval = setInterval(function() {
				countdownValue--;
				forgetemailcountdown.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("forgetemailcheckbtn").style.display = 'none';
					forgetemailcountdown.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}




		if (!countdownInterval) {
			countdownInterval = setInterval(function() {
				countdownValue--;
				forgetemailcountdown.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("forgetemailcheckbtn").style.display = 'none';
					forgetemailcountdown.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}

		forgetgetAllComments();
		}
	});

function forgetgetAllComments() {
		let email = document.querySelector('input#forgetpwemail').value;
		console.log(email);

		if (email.indexOf('@') === -1) {
			forgetemailwarningmodal.style.display = 'block';
			document.querySelector('input#forgetpwemail').value='';
		} else {
			forgetcheckemailbox.style.display = 'block';

			const uri = `login/email/${email}`;
			console.log(uri);

			axios.get(uri)
				.then((response) => {
					console.log(response.data);

					const forgetemailcheckbtn = document.querySelector("a#forgetemailcheckbtn");

					forgetemailcheckbtn.addEventListener('click', () => {
						const forgetemailcheck = document.querySelector('input#forgetemailcheck').value;
						console.log(forgetemailcheck);
						console.log(response.data);
						if (response.data == forgetemailcheck) {
							console.log("성공");
							forgetemailcheckbtn.style.display = 'none';
							forgetemailcountdown.style.display = 'none';
							forgetemailsuccess.style.display = 'block';
							forgetemailinsert.style.display = 'none';
							document.querySelector('input#forgetpwemail').setAttribute('readonly', 'readonly');
							document.querySelector('input#forgetemailcheck').setAttribute('readonly', 'readonly');
							document.querySelector('input#forgetpwfullname').setAttribute('readonly', 'readonly');
				

						} else {
							console.log("인증비번실패");
							forgetemailnumberwarningmodal.style.display = 'block';
							document.querySelector('input#forgetemailcheck').value='';
						}

					});

				})
				.catch((error) => {
					console.log(error);

				});

		}
	}


	function getAllComments() {
		let email = document.querySelector('input#logemail').value;
		console.log(email);

		if (email.indexOf('@') === -1) {
			warningmodal.style.display = 'block';
		} else {
			ifemailinsert.style.display = 'block';

			const uri = `login/email/${email}`;
			console.log(uri);

			axios.get(uri)
				.then((response) => {
					console.log(response.data);

					const emailcheckbtn = document.querySelector("a#emailcheckbtn");

					emailcheckbtn.addEventListener('click', () => {
						const checkemail = document.querySelector('input#checkmail').value;
						console.log(checkemail);
						console.log(response.data);
						if (response.data == checkemail) {
							console.log("성공");
							emailcheckbtn.style.display = 'none';
							countdownElement.style.display = 'none';
							success.style.display = 'block';
							emailinsert.style.display = 'none';
							document.querySelector('input#logemail').setAttribute('readonly', 'readonly');
							document.querySelector('input#checkmail').setAttribute('readonly', 'readonly');
							checkidChecked = true;
							console.log(idChecked);
							
				if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }

						} else {
							console.log("인증비번실패");
							numberwarningmodal.style.display = 'block';
						}

					});

				})
				.catch((error) => {
					console.log(error);

				});

		}
	}

	let passwordinput = document.querySelector("input#passwordinput");
	let passwordcheck = document.querySelector("input#passwordcheck");
	let passwordcorrect = document.querySelector("#passwordcorrect");
	let passwordwrong = document.querySelector("#passwordwrong");
	let passwordcondition = document.querySelector("#passwordcondition");
	let passwordgood = document.querySelector("#passwordgood");

	passwordinput.addEventListener('input', function() {
		let password = passwordinput.value;

		// 각 조건을 확인
		let hasMinLength = password.length >= 10;
		let hasUpperCase = /[A-Z]/.test(password);
		let hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

		// 모든 조건을 충족하면 스타일 조정
		if (hasMinLength && hasUpperCase && hasSpecialChar && password.length <=30) {
			passwordgood.style.display = 'block';
			passwordcondition.style.display = 'none';
			if (passwordinput.value === passwordcheck.value) {
			console.log(passwordinput.value);
			console.log(passwordcheck.value);
			passwordcorrect.style.display = 'block';
			passwordwrong.style.display = 'none';
		} else {
			passwordcorrect.style.display = 'none';
			passwordwrong.style.display = 'block';
		}
			
		} else {
			passwordcondition.style.display = 'block';
			passwordgood.style.display = 'none';
			if (passwordinput.value === passwordcheck.value) {
			console.log(passwordinput.value);
			console.log(passwordcheck.value);
			passwordcorrect.style.display = 'block';
			passwordwrong.style.display = 'none';
		} else {
			passwordcorrect.style.display = 'none';
			passwordwrong.style.display = 'block';
		}
		}
	});

	passwordcheck.addEventListener('input', () => {
		if (passwordinput.value === passwordcheck.value) {
			console.log(passwordinput.value);
			console.log(passwordcheck.value);
			passwordcorrect.style.display = 'block';
			passwordwrong.style.display = 'none';
		} else {
			passwordcorrect.style.display = 'none';
			passwordwrong.style.display = 'block';
		}


	});






let newpassword = document.querySelector("input#newpassword");
	let newpasswordcheck = document.querySelector("input#newpasswordcheck");
	let newpasswordwarngoodinfo = document.querySelector("p#newpasswordwarngoodinfo");
	let newpasswordwarninfo = document.querySelector("p#newpasswordwarninfo");
	let newpasswordmatchno = document.querySelector("p#newpasswordmatchno");
    let newpasswordmatchgood = document.querySelector("p#newpasswordmatchgood");

	newpassword.addEventListener('input', function() {
		let password = newpassword.value;

		// 각 조건을 확인
		let hasMinLength = password.length >= 10;
		let hasUpperCase = /[A-Z]/.test(password);
		let hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

		// 모든 조건을 충족하면 스타일 조정
		if (hasMinLength && hasUpperCase && hasSpecialChar && password.length <=30) {
			newpasswordwarninfo.style.display = 'none';
			newpasswordwarngoodinfo.style.display = 'block';
			if (newpassword.value === newpasswordcheck.value) {
			console.log(newpassword.value);
			console.log(newpasswordcheck.value);
			newpasswordmatchgood.style.display = 'block';
			newpasswordmatchno.style.display = 'none';
	
			
			
		} else {
			newpasswordmatchgood.style.display = 'none';
			newpasswordmatchno.style.display = 'block';

		}
			
			
			 
		} else {
			 newpasswordwarninfo.style.display = 'block';
			newpasswordwarngoodinfo.style.display = 'none';
			if (newpassword.value === newpasswordcheck.value) {
			console.log(newpassword.value);
			console.log(newpasswordcheck.value);
			newpasswordmatchgood.style.display = 'block';
			newpasswordmatchno.style.display = 'none';
			
		} else {
			newpasswordmatchgood.style.display = 'none';
			newpasswordmatchno.style.display = 'block';
			
		}
		
		}
	});

	newpasswordcheck.addEventListener('input', () => {
		if (newpassword.value === newpasswordcheck.value) {
			console.log(newpassword.value);
			console.log(newpasswordcheck.value);
			newpasswordmatchgood.style.display = 'block';
			newpasswordmatchno.style.display = 'none';
			
		} else {
			newpasswordmatchgood.style.display = 'none';
			newpasswordmatchno.style.display = 'block';
		
		}


	});


let newpasswordchange = document.querySelector('#newpasswordchange');

newpasswordchange.addEventListener('click',()=>{
			let password = newpassword.value;
			let forgetpwfullname = document.querySelector('input#forgetpwfullname').value;
			let forgetpwemail = document.querySelector('input#forgetpwemail').value;
			

			let name =forgetpwfullname;
			let email =forgetpwemail;
		
		// 각 조건을 확인
		let hasMinLength = password.length >= 10;
		let hasUpperCase = /[A-Z]/.test(password);
		let hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

		// 모든 조건을 충족하면 스타일 조정
		if (hasMinLength && hasUpperCase && hasSpecialChar && password.length <=30 && newpassword.value === newpasswordcheck.value){
				
				
				const uri = `login/findpassword/${email}/${name}/${password}`;
					console.log(uri);
		
					axios.get(uri)
						.then((response) => {

							console.log(response.data);
							
						
					window.location.href="http://localhost:8081/login"


				})
				.catch((error) => {
					
					console.log(error);
					
				});
			
			
		} else{
		 return;
		}

});

	function getphone() {
		let phone = document.querySelector('input#phone').value;
		console.log(phone);

		// 동일한 숫자가 count 이상 반복되는지 확인하는 함수
		function hasRepeatedNumbers(str, count) {
			const regex = new RegExp(`(\\d)\\1{${count - 1},}`, 'g');
			return regex.test(str);
		}

		if ((phone.length >= 12 || phone.length <= 8) || hasRepeatedNumbers(phone, 6)) {
			phonewarningmodal.style.display = 'block';
			document.querySelector('input#phone').value = '';
		} else {
			ifphoneinsert.style.display = 'block';




			const uri = `login/phone/${phone}`;
			console.log(uri);

			axios.get(uri)
				.then((response) => {

					console.log(response.data);
					const phonecheckbtn = document.querySelector("a#phonecheckbtn");

					phonecheckbtn.addEventListener('click', () => {
						const phonecheck = document.querySelector('input#phonecheck').value;
						console.log(phonecheck);
						console.log(response.data);
						if (response.data == phonecheck) {
							console.log("성공");
							phonecheckbtn.style.display = 'none';
							phonecountdownElement.style.display = 'none';
							phonesuccess.style.display = 'block';
							phoneinsert.style.display = 'none';
							document.querySelector('input#phone').setAttribute('readonly', 'readonly');
							document.querySelector('input#phonecheck').setAttribute('readonly', 'readonly');
							checkphoneChecked = true;
							console.log(idChecked);
							console.log(pwdChecked);
							console.log(nameChecked);
							console.log(nicknameChecked);
							console.log(passwordcheckChecked);
							console.log(checkidChecked);
							console.log(checkphoneChecked);
			if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
	         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }

						} else {
							console.log("인증비번실패");

							phonenumberwarningmodal.style.display = 'block';
							document.querySelector('input#phonecheck').value='';
						}

					});



				})
				.catch((error) => {
					console.log(error);

				});
		}
	}


//키로 어플리케이션 연동
window.Kakao.init("dd5d6f37293f08e3f8b524c5c3f0f326");
function kakaologin(){
	window.Kakao.Auth.login({
		scope: 'profile_nickname, profile_image, account_email',
	 	success: function(authObj){
			 console.log(authObj);
			 
			 const accessToken = authObj.access_token;
            console.log('Access Token:', accessToken);
			 
			 window.Kakao.API.request({
				 url:'/v2/user/me',
				 success: res => {
					 const kakao_account = res.kakao_account;
					 console.log(kakao_account);
					 const profile = kakao_account.profile;
                	   console.log(profile.nickname);
                	   console.log(profile.profile_image_url);
                	   console.log(kakao_account.email);
                	   
                	  	
				
		let email = kakao_account.email;
	 document.getElementById('naverEmail').value = email;
 		const checkuri = `login/checkemail/${email}`;
		console.log(checkuri);
		
		
		axios.get(checkuri)
				.then((response) => {

					console.log(response.data);
					if(response.data ==='Y'){
							
				let email = kakao_account.email;
				let name  =  profile.nickname;
				let password = "naverpassword";
				let nicknamechange = kakao_account.email;
				nickname = nicknamechange.replace(".com", "");
   				console.log(nickname);
   				let usersaveprofile = profile.profile_image_url;
				let phone = "kakaophone";
				
				
				 const data = {
		   			email,
		  			 name,
		  			 password,
		   			nickname,
		  			 phone,
		  			 usersaveprofile
				 };
          		          
  axios.post('login/naver', data)
   .then((response) => {
     console.log(response.data);
	  var hibbenButton = document.getElementById('hibbenbutton');
	  hibbenButton.click();


    
   })
   .catch((error) => {
     console.log(error);
	  var hibbenButton = document.getElementById('hibbenbutton');
	  hibbenButton.click();	
   });
					} 
					  var hibbenButton = document.getElementById('hibbenbutton');
					  hibbenButton.click();	
					
				     
				})
				.catch((error) => {
					console.log(error);
					  var hibbenButton = document.getElementById('hibbenbutton');
					  hibbenButton.click();	
				});
                    
 }  
 
 
 
			 });
		 }
	});
	
	   
}

  // 링크에 클릭 이벤트 리스너 추가하여 카카오 로그인 함수 호출
            var kakaologinLink = document.getElementById('kakaologin');
            if (kakaologinLink) {
                kakaologinLink.addEventListener('click', function (event) {
                    event.preventDefault(); // 기본 동작 방지
                    kakaologin(); // 카카오 로그인 함수 호출
         
         document.cookie = "userId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
	//TODO 로그인시 쿠키에 계속 아이디정보가 남아있어서 자동로그인이 되는데 로그아웃시에 남아있던 아이디정보를 없애고 리셋시키고 싶다 
        var accessToken = Kakao.Auth.getAccessToken();
        console.log('Access Token before logout:', accessToken);

        if (accessToken) {
           Kakao.Auth.logout(function() {
   				 var accessTokenAfterLogout = Kakao.Auth.getAccessToken();
    			console.log('Access Token after logout:', accessTokenAfterLogout);
    			
    		
    			 window.location.href = 'http://localhost:8081/';

			});

        } else {
           
        }
                });
            }
            
    var kakaologout=document.getElementById('kakaologout');

if (kakaologout) {
    kakaologout.addEventListener('click', function () {
         document.cookie = "userId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
	//TODO 로그인시 쿠키에 계속 아이디정보가 남아있어서 자동로그인이 되는데 로그아웃시에 남아있던 아이디정보를 없애고 리셋시키고 싶다 
        var accessToken = Kakao.Auth.getAccessToken();
        console.log('Access Token before logout:', accessToken);

        if (accessToken) {
           Kakao.Auth.logout(function() {
   				 var accessTokenAfterLogout = Kakao.Auth.getAccessToken();
    			console.log('Access Token after logout:', accessTokenAfterLogout);
    			alert('Logout successful');
			});

        } else {
            alert('Not logged in');
        }
    });
} else {
    console.error("Element with id 'kakaologout' not found.");
}
let forgetfullnames = document.querySelector('input#forgetfullname');
let forgetphone = document.querySelector('input#forgetphone');
let forgetphonecheck = document.querySelector('input#forgetphonecheck');


forgetfullnames.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >=10){
			e.target.value=null;
			return;}	
});

forgetphone.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >=30){
			e.target.value=null;
			return;}	
});


	forgetphone.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});

forgetphonecheck.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >4){
			e.target.value=null;
			return;}	
});


forgetphonecheck.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});

let findemailbutton = document.querySelector('button#findemailbutton');
let finallyfindemail = document.querySelector('div#finallyfindemail');
let finallyfindemailinfo =document.querySelector('p#finallyfindemailinfo');
let finallyfindemailclose = document.querySelector('#finallyfindemailclose');
let failfinallyfindemail = document.querySelector('div#failfinallyfindemail');
let failfinallyfindemailclose = document.querySelector('#failfinallyfindemailclose');
finallyfindemailclose.addEventListener('click', () => {
		finallyfindemailinfo.innerHTML = '';
		finallyfindemail.style.display = 'none';
		window.location.href= 'http://localhost:8081/login';

	});
	failfinallyfindemailclose.addEventListener('click', () => {
		failfinallyfindemail.style.display = 'none';
		window.location.href= 'http://localhost:8081/login';

	});

let forgethavetoinput = document.querySelector('div#forgethavetoinput');
let forgethavetoinputclose = document.querySelector('#forgethavetoinputclose');

	forgethavetoinputclose.addEventListener('click', () => {
		forgethavetoinput.style.display = 'none';

	});

findemailbutton.addEventListener('click',()=>{	
	let forgetfullname = document.querySelector('input#forgetfullname').value;
	let forgetphone = document.querySelector('input#forgetphone').value;
	let forgetphonecheckInput = document.querySelector('input#forgetphonecheck');
	console.log(forgetfullname);
	console.log(forgetphone);
	let name =forgetfullname;
	let phone = forgetphone;
	
		  if (forgetfullname.trim() === '' || forgetphone.trim() === '' || !forgetphonecheckInput.readOnly) {
    // 값이 비어있을 경우 처리 (예: 경고 메시지 등)
    forgethavetoinput.style.display='block';
    return; // 클릭 이벤트 중지
  	}

	
	const uri = `login/findemail/${name}/${phone}`;
					console.log(uri);
		
					axios.get(uri)
						.then((response) => {

							console.log(response.data);
							
							if(response.data !== ''){
								 
								 finallyfindemailinfo.innerHTML = response.data;
								 finallyfindemail.style.display = 'block';
							} else {
								failfinallyfindemail.style.display = 'block';

							}
							
					


				})
				.catch((error) => {
					
					console.log(error);
					
				});
	
	
	
	
		
});

let forgetemailhavetoinput = document.querySelector('div#forgetemailhavetoinput');
let forgetemailhavetoinputclose = document.querySelector('#forgetemailhavetoinputclose');
	forgetemailhavetoinputclose.addEventListener('click', () => {
		forgetemailhavetoinput.style.display = 'none';
		
	});
let finallyfindpassword = document.querySelector('div#finallyfindpassword');
let finallyfindpasswordclose = document.querySelector('#finallyfindpasswordclose');
	finallyfindpasswordclose.addEventListener('click', () => {
		finallyfindpassword.style.display = 'none';
		window.location.href="http://localhost:8081/login"
		
	});
let failfinallyfindpassword = document.querySelector('div#failfinallyfindpassword');
let failfinallyfindpasswordclose = document.querySelector('#failfinallyfindpasswordclose');
	failfinallyfindpasswordclose.addEventListener('click', () => {
		failfinallyfindpassword.style.display = 'none';
		window.location.href="http://localhost:8081/login"
		
	});	
let findpasswordbutton = document.querySelector('button#findpasswordbutton');	

findpasswordbutton.addEventListener('click',()=>{	
	let forgetpwfullname = document.querySelector('input#forgetpwfullname').value;
	let forgetpwemail = document.querySelector('input#forgetpwemail').value;
	let forgetpwemailcheck = document.querySelector('input#forgetemailcheck');

	let name =forgetpwfullname;
	let email =forgetpwemail;
	
		  if (forgetpwfullname.trim() === '' || forgetpwemail.trim() === '' || !forgetpwemailcheck.readOnly) {
    // 값이 비어있을 경우 처리 (예: 경고 메시지 등)
    forgetemailhavetoinput.style.display='block';
    return; // 클릭 이벤트 중지
  	}

	
	const uri = `login/findpassword/${email}/${name}`;
					console.log(uri);
		
					axios.get(uri)
						.then((response) => {

							console.log(response.data);
							
							if(response.data !== ''){
								 
				
								 finallyfindpassword.style.display = 'block';
							} else {
								failfinallyfindpassword.style.display = 'block';

							}
							
					


				})
				.catch((error) => {
					
					console.log(error);
					
				});
	
	
	
	
		
});

let signupbtn = document.querySelector('button#signupbtn');

  signupbtn.setAttribute('disabled', 'disabled');



const inputemail = document.querySelector('input#logemail');
	inputemail.addEventListener('change',checkUserid);
	const inputPassword = document.querySelector('input#passwordinput');
	inputPassword.addEventListener('change',checkPassword);
	const inputCheckPassword = document.querySelector('input#passwordcheck');
	inputCheckPassword.addEventListener('change',checkPasswordcheck);
	const inputName = document.querySelector('input#name');
	inputName.addEventListener('change',checkName);
	const inputNickname = document.querySelector('input#nickname');
	inputNickname.addEventListener('change',checkNickname);
	const inputPhone = document.querySelector('input#phone');
	inputPhone.addEventListener('change',checkPhone);
	
	inputPhone.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});
	
	window.addEventListener('keyup', function() {
        if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
            signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }
    })
	
	async function checkUserid(e){
		const email=e.target.value;
		const checkuri = `login/checkemail/${email}`;
		console.log(checkuri);
		
		const uri = `login/checkemail/${email}`
		const response= await axios.get(uri);
		
		const checkIdResult = document.querySelector('div#checkIdResult');
		if(e.target.value.length <=30){
		if(response.data ==='Y' && email.indexOf('@') !== -1){
			idChecked = true;
			checkIdResult.innerHTML = 'Available Email ID';
			checkIdResult.classList.remove('failid');
			checkIdResult.classList.add('successid');
		} else{
			idChecked = false;
			checkIdResult.innerHTML = 'Duplicate Email ID';
			checkIdResult.classList.remove('successid');
			checkIdResult.classList.add('failid');
			
		}}else{
			idChecked = false;
			e.target.value=null;
			return;
		}

	}
	
	
	
	function checkPassword(e){
		if(e.target.value==='' || e.target.value.length >=30 || passwordgood.style.display != 'block'){
			pwdChecked = false;
			e.target.value=null;
			return;
		} else{
			pwdChecked = true;
		}
		if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }
	}
	
		function checkPasswordcheck(e){
		if(e.target.value==='' || e.target.value.length >=30 || passwordcorrect.style.display != 'block'){
			passwordcheckChecked = false;
			e.target.value=null;
			return;
		} else{
			passwordcheckChecked = true;
		}
		if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }
	}
	
	
	function checkName(e){
		if(e.target.value==='' || e.target.value.length >=20){
			nameChecked = false;
			e.target.value=null;
			return;
		} else {
			nameChecked = true;
		}
		if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked  && passwordcheckChecked && checkidChecked && checkphoneChecked){
         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }
	}
	

	
async function checkNickname(e){
		const nickname=e.target.value;
		const uri = `login/checknickname/${nickname}`;
		const response= await axios.get(uri);
		
		const checkNicknameResult = document.querySelector('div#checkNicknameResult');
		if(e.target.value.length <=20){
		if(response.data ==='Y'){
			nicknameChecked = true;
			checkNicknameResult.innerHTML = 'Available Nickname';
			checkNicknameResult.classList.remove('failid');
			checkNicknameResult.classList.add('successid');
		} else{
			nicknameChecked = false;
			checkNicknameResult.innerHTML = 'Duplicate Nickname';
			checkNicknameResult.classList.remove('successid');
			checkNicknameResult.classList.add('failid');
		}} else{
			nicknameChecked = false;
			e.target.value=null;
			return;
			
		}	
			if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }
		}
async function checkPhone(e){

		const phone=e.target.value;
		const uri = `login/checkphone/${phone}`;
		const response= await axios.get(uri);
		
		const checkphoneResult = document.querySelector('div#checkphoneResult');
		if(e.target.value.length <=20){
		if(response.data ==='Y'){
			phoneChecked = true;
			checkphoneResult.innerHTML = 'Available Phone';
			checkphoneResult.classList.remove('failid');
			checkphoneResult.classList.add('successid');
		} else{
			phoneChecked =  false;
			checkphoneResult.innerHTML = 'Duplicate Phone';
			checkphoneResult.classList.remove('successid');
			checkphoneResult.classList.add('failid');
		}} else{
			phoneChecked =  false;
			e.target.value=null;
			return;
			
		}	
			if(idChecked && pwdChecked && nameChecked && nicknameChecked && phoneChecked && passwordcheckChecked && checkidChecked && checkphoneChecked){
         signupbtn.removeAttribute('disabled');
        } else {
             signupbtn.setAttribute('disabled', 'disabled');
        }
		
	}

});