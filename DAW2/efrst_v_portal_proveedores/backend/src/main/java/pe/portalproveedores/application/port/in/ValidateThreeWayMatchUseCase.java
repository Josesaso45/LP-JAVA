package pe.portalproveedores.application.port.in;

import pe.portalproveedores.domain.model.ThreeWayMatchResult;
import pe.portalproveedores.domain.model.ValidateMatchCommand;

public interface ValidateThreeWayMatchUseCase {

    ThreeWayMatchResult validate(ValidateMatchCommand command);
}
