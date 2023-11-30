
package mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.sisec.cuentaindividual package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCuentaIndividualSISECResponse_QNAME = new QName("http://cuentaIndividual.sisec.imss.gob.mx/", "getCuentaIndividualSISECResponse");
    private final static QName _GetCuentaIndividualSISEC_QNAME = new QName("http://cuentaIndividual.sisec.imss.gob.mx/", "getCuentaIndividualSISEC");
    private final static QName _RespuestaWSMensaje_QNAME = new QName("", "mensaje");
    private final static QName _RespuestaWSClave_QNAME = new QName("", "clave");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.sisec.cuentaindividual
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CuentaIndividualVo }
     * 
     */
    public CuentaIndividualVo createCuentaIndividualVo() {
        return new CuentaIndividualVo();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link RespuestaCuentaIndividual }
     * 
     */
    public RespuestaCuentaIndividual createRespuestaCuentaIndividual() {
        return new RespuestaCuentaIndividual();
    }

    /**
     * Create an instance of {@link GetCuentaIndividualSISECResponse }
     * 
     */
    public GetCuentaIndividualSISECResponse createGetCuentaIndividualSISECResponse() {
        return new GetCuentaIndividualSISECResponse();
    }

    /**
     * Create an instance of {@link GetCuentaIndividualSISEC }
     * 
     */
    public GetCuentaIndividualSISEC createGetCuentaIndividualSISEC() {
        return new GetCuentaIndividualSISEC();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCuentaIndividualSISECResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cuentaIndividual.sisec.imss.gob.mx/", name = "getCuentaIndividualSISECResponse")
    public JAXBElement<GetCuentaIndividualSISECResponse> createGetCuentaIndividualSISECResponse(GetCuentaIndividualSISECResponse value) {
        return new JAXBElement<GetCuentaIndividualSISECResponse>(_GetCuentaIndividualSISECResponse_QNAME, GetCuentaIndividualSISECResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCuentaIndividualSISEC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cuentaIndividual.sisec.imss.gob.mx/", name = "getCuentaIndividualSISEC")
    public JAXBElement<GetCuentaIndividualSISEC> createGetCuentaIndividualSISEC(GetCuentaIndividualSISEC value) {
        return new JAXBElement<GetCuentaIndividualSISEC>(_GetCuentaIndividualSISEC_QNAME, GetCuentaIndividualSISEC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mensaje", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMensaje(String value) {
        return new JAXBElement<String>(_RespuestaWSMensaje_QNAME, String.class, RespuestaWS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "clave", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSClave(String value) {
        return new JAXBElement<String>(_RespuestaWSClave_QNAME, String.class, RespuestaWS.class, value);
    }

}
