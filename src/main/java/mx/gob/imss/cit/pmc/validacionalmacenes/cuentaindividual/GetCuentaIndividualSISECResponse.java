
package mx.gob.imss.cit.pmc.validacionalmacenes.cuentaindividual;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCuentaIndividualSISECResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCuentaIndividualSISECResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://cuentaIndividual.sisec.imss.gob.mx/}respuestaCuentaIndividual" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCuentaIndividualSISECResponse", propOrder = {
    "_return"
})
public class GetCuentaIndividualSISECResponse {

    @XmlElement(name = "return")
    protected RespuestaCuentaIndividual _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaCuentaIndividual }
     *     
     */
    public RespuestaCuentaIndividual getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaCuentaIndividual }
     *     
     */
    public void setReturn(RespuestaCuentaIndividual value) {
        this._return = value;
    }

}
