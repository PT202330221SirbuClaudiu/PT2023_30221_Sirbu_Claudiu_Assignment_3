package bll.validators;

import model.Produs;
import presentation.Pop;

import javax.management.BadAttributeValueExpException;

public class ValidatorCantitate implements Validator<Produs> {
    private  Integer cantitateCeruta;

    public ValidatorCantitate(Integer cantitateCeruta)
    {
        this.cantitateCeruta=cantitateCeruta;
    }

    @Override
    public void validate(Produs produs) {
        try {
            if (produs.getCantitate() < cantitateCeruta) {
                throw new BadAttributeValueExpException("Nu sunt suficiente produse pe stoc");
            }
        } catch (BadAttributeValueExpException e) {
           Pop pop=new Pop();
        }

    }
}
