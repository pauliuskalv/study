#include <stdio.h>
#include <stdlib.h>
#include "medis.h"
#include "medis_tipas.h"
#define DYDIS 256

int main()
{
    reiksme array [8] = {1,2,3,4,5,6,7,8} ;
    medis mano ;
    mano = kurk_tuscia_medi(mano);
    iterpk ( mano , 'a' ) ;
    spausdink_medi(mano,'d');
    istrink_medi(mano);
    return 0;
}
