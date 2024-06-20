package Modelo

import oracle.sql.DATE
import oracle.sql.TIMESTAMP

class dataClassSucursal (
    val diaCita : DATE ,
    val horaCita : TIMESTAMP,
    val motivo : String ,
    val ID_Centro : Int,
    val ID_Paciente : Int
)
