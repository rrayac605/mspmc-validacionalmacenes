
package mx.gob.imss.sisec.cuentaindividual;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getHistoricoCentralSISECResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getHistoricoCentralSISECResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://cuentaIndividual.sisec.imss.gob.mx/}respuestaHistoricoCuentaIndividual" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getHistoricoCentralSISECResponse", propOrder = {
    "_return"
})
public class GetHistoricoCentralSISECResponse {

    @XmlElement(name = "return")
    protected RespuestaHistoricoCuentaIndividual _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaHistoricoCuentaIndividual }
     *     
     */
    public RespuestaHistoricoCuentaIndividual getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaHistoricoCuentaIndividual }
     *     
     */
    public void setReturn(RespuestaHistoricoCuentaIndividual value) {
        this._return = value;
    }

}
