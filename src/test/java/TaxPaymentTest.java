import org.informatics.TaxPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class TaxPaymentTest {

    @Test
    public void testCalculatePetFee() {
        TaxPayment taxPayment = new TaxPayment();
        double expectedFee = 3.0;  //Expected fee depends on the amount of animals in the apartment && if pet.type == 'Dog' in the table
        double actualFee = taxPayment.calculatePetFee(1);
        assertEquals(expectedFee, actualFee, "The calculated pet fee is incorrect.");
    }


    @Test
    public void testCalculateSquareFootageFee() {
        TaxPayment taxPayment = new TaxPayment();
        double expectedFee = 18.0; //SquareFootage of Apartment with ID 4 is 90.0
        double actualFee = taxPayment.calculateSquareFootageFee(4);
        assertEquals(expectedFee, actualFee, "The calculated square footage fee is incorrect.");
    }

    @Test
    public void testCalculateElevatorUsageFee() {
        TaxPayment taxPayment = new TaxPayment();
        double expectedFee = 4.0; //Value depends on the people that are using Elevator && age > 7
        double actualFee = taxPayment.calculateElevatorUsageFee(1);
        assertEquals(expectedFee, actualFee, "The calculated elevator usage fee is incorrect.");
    }

}