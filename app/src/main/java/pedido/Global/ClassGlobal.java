package pedido.Global;

import android.app.Application;



/**
 * Created by Alejandro on 04/11/2017.
 * CLASE GLOBAL PARA PODER ACCEDER A VALORES DE CUALQUIER LUGAR DE LA APP
 */

public class ClassGlobal extends Application {

      public String dolar;
      public void setDolar(String d){
          this.dolar = d;
      }
      public String getDolar(){
          return dolar;
      }

      private static ClassGlobal singleton;

        public static ClassGlobal getInstance() {
            if(singleton == null) {
                singleton = new ClassGlobal();
            }
            return singleton;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            singleton = this;
        }


}