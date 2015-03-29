/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validacioncedula;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author byronrodriguez
 */
public class VCedula{
    
    public static void main(String []args){
        boolean k = false;
        VCedula vced = new VCedula();
        String cedula = "1710032010";
        
        try {
            k = vced.verificacion(cedula);
        } catch (CedulaInvalidaException ex) {
            Logger.getLogger(VCedula.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(k){System.out.println(cedula + " OK");}
        
     }
    
    public boolean verificacion(String cedula) throws CedulaInvalidaException{
        Boolean v_digitos = false;
        Boolean v_tercero = false;
        Boolean v_provincia = false;
        Boolean v_ultimo = false;
        int k = 0;
        int a;
        int prov;
        int[] coef = {2,1,2,1,2,1,2,1,2};
        int[] ced = {0,0,0,0,0,0,0,0,0,0};
        
        for(int n = 0 ; n<=cedula.length()-1 ; n++){
            ced[n] = Character.getNumericValue(cedula.charAt(n));
        }
        
        //Valida numero valido con 10 digitos
        v_digitos = cedula.matches("[0-9]{10}");
        if(!v_digitos){throw new CedulaInvalidaException("Error: Numero debe contener 10 digitos");}
        
        //Valida tercer digito menor a 6
        if(ced[2] < 6){v_tercero = true;}
        if(!v_tercero){throw new CedulaInvalidaException("Error: Tercer digito debe ser menor a 6");}
       
        //Valida dos primeros digitos entre 01 y 24 
        prov = ced[0]*10 + ced[1]; 
        if(prov >= 1 && prov <= 24){v_provincia = true;}
        if(!v_provincia){throw new CedulaInvalidaException("Error: Dos primeros digitos deben corresponder a codigo de provincia");}  
        
        
        //Valida ultimo digito con algoritmo de verificacion
        for(int n = 0 ; n<=8 ; n++){
            a = ced[n] * coef[n];
            if( a >=10 ){a = a - 9;}
            k = k + a;
        }        
        int v = (k/10+1)*10-k;
        if (v >= 10){v = 0;} 
        if (v == ced[9]){v_ultimo = true;}
        if(!v_ultimo){throw new CedulaInvalidaException("Error: Digito verificador no coincide");}
        
        
 
        return(v_digitos && v_tercero && v_provincia && v_ultimo);
    
    }
            
    
 
}
