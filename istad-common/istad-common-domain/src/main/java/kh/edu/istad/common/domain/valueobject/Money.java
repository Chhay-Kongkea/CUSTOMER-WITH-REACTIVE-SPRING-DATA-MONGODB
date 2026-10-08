package kh.edu.istad.common.domain.valueobject;

import java.math.BigDecimal;


// 150$ new Money(150)
// 100$ new Money(100)

public record Money(
        BigDecimal amount
) {

   public void isGreaterThanZero(){

       if(!(amount.compareTo(BigDecimal.ZERO) > 0)){
           System.out.println("This number greater that 0");
           throw new RuntimeException("This number greater that 0");

       }

   }


}
