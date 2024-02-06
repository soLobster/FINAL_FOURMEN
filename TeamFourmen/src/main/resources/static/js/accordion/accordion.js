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
              }, 450); 
              
              if (button != null) {
                  button.style.height = 0;              
                  button.style.margin = 0; 
              }

          }
          
           
       });
        
    });
    
});