package be.vdab.soapexceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class InvalidCountryCodeException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public InvalidCountryCodeException(String code) {
    super("Invalid code:" + code);
  }
}
