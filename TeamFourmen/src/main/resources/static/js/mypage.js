/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	let nickname = document.querySelector("#nickname");
	let phone = document.querySelector("#phone");
	let phonecheck = document.querySelector("#phonecheck");
	let phonecountdownElement = document.getElementById("phonetime");
	let ifphoneinsert = document.querySelector('#ifphoneinsert');
	let countdownInterval;
	let phonenumberwrong = document.querySelector('#phonenumberwrong');
	let phonenumbercorrect = document.querySelector('#phonenumbercorrect');
	let phoneChecked = true;
	let nicknameChecked = true;
	let checkphoneChecked= true;
	
	let userphone = document.querySelector("#userphone");
	let userphonecheck = document.querySelector("#userphonecheck");
	
	
	// 버튼 요소 선택
let btnupdate = document.querySelector('#btnupdate');
btnupdate.setAttribute('disabled', 'disabled');
// 활성화/비활성화할 요소들 선택

	
	
		userphone.addEventListener('click', () => {
		document.getElementById("userphonecheck").style.pointerEvents = "auto";

		countdownValue = 180;
		phonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

		if (countdownInterval) {
			clearInterval(countdownInterval);
			document.getElementById("userphonecheck").style.display = 'block';
			countdownInterval = setInterval(function() {
				countdownValue--;
				phonecountdownElement.textContent = "Please enter the verification number within " + countdownValue + " seconds";

				if (countdownValue <= 0) {
					// 초수가 0 이하로 내려가면 clearInterval로 간격 제거
					clearInterval(countdownInterval);
					document.getElementById("userphonecheck").style.display = 'none';
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
					document.getElementById("userphonecheck").style.display = 'none';
					phonecountdownElement.textContent = "Send me the verification number again";
				}
			}, 1000); // 1000 밀리초 = 1초
		}


		getphone();
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
			document.querySelector('input#phone').value = '';
		} else {
			ifphoneinsert.style.display = 'block';




			const uri = `/login/phone/${phone}`;
			console.log(uri);

			axios.get(uri)
				.then((response) => {

					console.log(response.data);
					

				userphonecheck.addEventListener('click', () => {
						const phonecheck = document.querySelector('input#phonecheck').value;
						console.log(phonecheck);
						console.log(response.data);
						if (response.data == phonecheck) {
							console.log("성공");
							checkphoneChecked = true;
							userphonecheck.style.display = 'none';
							phonecountdownElement.style.display = 'none';
							phonenumberwrong.style.display = 'none';
							phonenumbercorrect.style.display = 'block';
							userphone.style.display = 'none';
							document.querySelector('input#phone').setAttribute('readonly', 'readonly');
							document.querySelector('input#phonecheck').setAttribute('readonly', 'readonly');
							
									if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	


						} else {
							console.log("인증비번실패");

							phonenumberwrong.style.display = 'block';
							document.querySelector('input#phonecheck').value='';
							checkphoneChecked= false;
									if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	
						}

					});



				})
				.catch((error) => {
					console.log(error);

				});
		}
	}
	
	window.addEventListener('keyup', function() {
		if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	
    });
	
	nickname.addEventListener('change',checknickname);
	phone.addEventListener('change',checkphone);
	
	phone.addEventListener('input',function(event){
		 const phonecondition =event.target.value.replace(/[^0-9]/g, '');
		 
		  event.target.value = phonecondition;
		   checkphoneChecked= false;
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

	
	async function checkphone(e){

		const phone=e.target.value;
		
		const uri = `/login/checkphone/${phone}`;
		const response= await axios.get(uri);
		
		
		const checkphoneResult = document.querySelector('div#checkphoneResult');
		if(e.target.value.length <=20){
		if(response.data ==='Y'){
			phoneChecked = true;
			checkphoneResult.innerHTML =  'Available Phone';
			checkphoneResult.classList.remove('failid');
			checkphoneResult.classList.add('successid');
			userphone.style.display = 'block';
		} else{
			phoneChecked =  false;
			checkphoneResult.innerHTML = 'Duplicate Phone';
			checkphoneResult.classList.remove('successid');
			checkphoneResult.classList.add('failid');
			userphone.style.display = 'none';
			btnupdate.setAttribute('disabled', 'disabled');
		}} else{
			btnupdate.setAttribute('disabled', 'disabled');
			phoneChecked =  false;
			e.target.value=null;
			return;
			
		}	
				if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	

		
	}

async function checknickname(e){
		const nickname=e.target.value;
		const uri = `/login/checknickname/${nickname}`;
		const response= await axios.get(uri);
		
		const checkNicknameResult = document.querySelector('div#checkNicknameResult');
		if(e.target.value.length <=20 && e.target.value.length>=2){
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
			btnupdate.setAttribute('disabled', 'disabled');
		}} else{
			btnupdate.setAttribute('disabled', 'disabled');
			checkNicknameResult.innerHTML = 'short in length';
			checkNicknameResult.classList.remove('successid');
			checkNicknameResult.classList.add('failid');
			nicknameChecked = false;
			e.target.value=null;
			return;
			
		}	
		if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	
		}	
	
	

		
	btnupdate.addEventListener('click',()=>{
		
		var form = document.getElementById("myForm");
		
		form.submit();
	});	
	
	document.getElementById('fileInput').addEventListener('change', function(e) {
  	  var preview = document.querySelector('#previewImage');

    var file    = document.querySelector('input[type=file]').files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
       
    }

    if (file) {
        reader.readAsDataURL(file);
        
                    									if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	
    } else {
        preview.src = "/image/userimage.png"; // 파일이 선택되지 않은 경우 이미지를 지웁니다.
      
      
           if(nicknameChecked && phoneChecked && checkphoneChecked){
        	btnupdate.removeAttribute('disabled');
        } else {
             btnupdate.setAttribute('disabled', 'disabled');
		}	
   
   
    }
        

    

});

document.querySelector('#btndelete').addEventListener('click', function() {
    let type= document.querySelector('#type').value;
   

    // 모달을 띄울 조건 확인
    if (type === "kakao" || type === "naver") {
 		
 		finallyfindpassword.style.display = 'block';
    } else{
		directbye.style.display = 'block';
	}
	});
	
	let deleteemailbye = document.querySelector('#deleteemailbye');

	 let parameterValue = document.querySelector('input#userid').value;
	 let newURL = "/mypage/delete?email=" + parameterValue;

    // href 속성에 새로운 주소 설정
   
	
	let deletedirectbye = document.querySelector('#deletedirectbye');

	let finallyfindpassword = document.querySelector('#finallyfindpassword');
	let finallyfindpasswordclose = document.querySelector('#finallyfindpasswordclose');
	let directbye=document.querySelector('#directbye');
	let directbyeclose = document.querySelector('#directbyeclose');
	let emailinsert= document.querySelector('#emailinsert');
	let ifemailinsert= document.querySelector('#ifemailinsert');
	let countdownElement = document.querySelector('#emailcountdown');
	let success =document.querySelector('#emailchecksuccess');
	let emailcheckbye = false;
	let checkemail = document.querySelector('#checkemail');
	let lastcheck =document.querySelector('input#lastcheck');
	let lastpasswordchecked = false;
	
	
	
	
	   var memberEmail = "${member.email}"; // 서버에서 전달된 변수
    

	
	lastcheck.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >20){
			e.target.value=null;
			return;}				
});
	
	lastcheck.addEventListener('change',checklastcheck);
		
	async function checklastcheck(e){
		const password=e.target.value;

		const uri = `/checkpassword/${password}`
		const response= await axios.get(uri);
		
		if(e.target.value.length <=25){
			console.log(response.data);
		if(response.data ==='Y'){
			
			lastpasswordchecked = true;
			 document.getElementById("deletedirectbye").setAttribute('href', newURL);
			
		} else{
			lastpasswordchecked = false;
	deletedirectbye.setAttribute('href', '/mypage');
						}
			
		}else{
			e.target.value=null;
			
			return;
		}

	}
	
	
	checkemail.addEventListener('change',(e)=>{
if(e.target.value==='' || e.target.value.length >4){
			e.target.value=null;
			return;}	
});

	checkemail.addEventListener('input', function(event) {
    // 입력된 값에서 숫자 이외의 문자를 제거합니다.
    const filteredValue = event.target.value.replace(/[^0-9]/g, '');
    
    // 제거된 값을 다시 입력 상자에 할당합니다.
    event.target.value = filteredValue;
});
	finallyfindpasswordclose.addEventListener('click', () => {
		finallyfindpassword.style.display = 'none';
	});
	
	directbyeclose.addEventListener('click', () => {
		directbye.style.display = 'none';
	});
	
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
	function getAllComments() {
		let email = document.querySelector('input#userid').value;
		console.log(email);


			ifemailinsert.style.display = 'block';

			const uri = `/login/email/${email}`;
			console.log(uri);

			axios.get(uri)
				.then((response) => {
					console.log(response.data);

					const emailcheckbtn = document.querySelector("a#emailcheckbtn");

					emailcheckbtn.addEventListener('click', () => {
						const checkemail = document.querySelector('input#checkemail').value;
						console.log(checkemail);
						console.log(response.data);
						if (response.data == checkemail) {
							console.log("성공");
							emailcheckbtn.style.display = 'none';
							countdownElement.style.display = 'none';
							success.style.display = 'block';
							emailinsert.style.display = 'none';
							document.querySelector('input#checkemail').setAttribute('readonly', 'readonly');
							emailcheckbye = true;
							
							 document.getElementById("deleteemailbye").setAttribute('href', newURL);

						} else {
							console.log("인증비번실패");
							document.querySelector('input#checkemail').value ='';
							location.reload();
						}

					});

				})
				.catch((error) => {
					console.log(error);

				});

		
	}
	
});	