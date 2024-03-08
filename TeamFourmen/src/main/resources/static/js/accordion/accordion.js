/**
 * accordion.js
 */

document.addEventListener('DOMContentLoaded', function() {
    
    const headers = document.querySelectorAll('.accordion-header');
    
    headers.forEach((header) => {
       
       header.addEventListener('click', function() {
          
          const container = header.closest('.accordion-container');
          const content = container.querySelector('.accordion-content');
          const button = container.querySelector('.accordion-btn');
          
          const symbolDown = container.querySelector('.accordion-symbol-down');
          const symbolUp = container.querySelector('.accordion-symbol-up');
          
          const contentHeight = content.style.height;          
          
          if (contentHeight == 0 || contentHeight == '0px') {
            content.style.height = content.scrollHeight + 'px';
            content.style.opacity = 1;
            symbolDown.style.display = 'none';
            symbolUp.style.display = 'block';
            
            if (button != null) {
                button.style.height = button.scrollHeight + 'px';            
                button.style.margin = '8px auto 0 auto';                
            }

          } else {              
              content.style.height = 0;
              
              symbolDown.style.display = 'block';
              symbolUp.style.display = 'none';
              
              setTimeout(() => {
                  content.style.opacity = 0;
              }, 550); 
              
              if (button != null) {
                  button.style.height = 0;              
                  button.style.margin = 0; 
              }

          }
          
           
       });
        
    });
    
// 아코디언 요소 선택
const accordionHeader = document.querySelector('.custom-card-header');

 // 클릭 이벤트 핸들러 추가
 accordionHeader.addEventListener('click', () => {
    // 헤더에 toggled 클래스가 있는지 확인
    const isToggled = accordionHeader.classList.contains('toggled');
    
    // 헤더에 toggled 클래스가 있으면 제거하고, 없으면 추가
    if (isToggled) {
        accordionHeader.classList.remove('toggled');
    } else {
        // 현재 클릭된 요소의 형제 요소들에서 toggled 클래스를 제거
        const siblings = accordionHeader.parentNode.querySelectorAll('.custom-card-header.toggled');
        siblings.forEach(sibling => sibling.classList.remove('toggled'));
        
        // 클릭된 요소에 toggled 클래스 추가
        accordionHeader.classList.add('toggled');
    }
});

// 아코디언 요소 선택
const accordionfooter = document.querySelector('.custom-card-footer');

 // 클릭 이벤트 핸들러 추가
 accordionfooter.addEventListener('click', () => {
    // 헤더에 toggled 클래스가 있는지 확인
    const isToggled = accordionfooter.classList.contains('toggled');
    
    // 헤더에 toggled 클래스가 있으면 제거하고, 없으면 추가
    if (isToggled) {
        accordionfooter.classList.remove('toggled');
    } else {
        // 현재 클릭된 요소의 형제 요소들에서 toggled 클래스를 제거
        const siblings = accordionfooter.parentNode.querySelectorAll('.custom-card-footer.toggled');
        siblings.forEach(sibling => sibling.classList.remove('toggled'));
        
        // 클릭된 요소에 toggled 클래스 추가
        accordionfooter.classList.add('toggled');
    }
});
});