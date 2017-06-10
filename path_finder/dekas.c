#include <stdio.h>
#include <stdlib.h>
#include "tipas_dekas.h"
#include "dekas.h"
dekas naujas_tuscias_dekas(){
    return NULL;
}
int ar_tuscias(dekas pradzia){
    if (pradzia == NULL)
        return 1;
    return 0;
}
int ideti_i_pabaiga (dekas *pradzia , TIPAS duom){
    if (ar_pilnas(*pradzia) == 1)
    return 0;
    dekas naujas , pabaiga = (*pradzia);
    if (ar_tuscias (*pradzia) == 1){
        (*pradzia) = calloc(1 , sizeof (struct dekas));
        (*pradzia)->duom = duom;
    }
    else{
        while(pabaiga->pirmyn != NULL){
            pabaiga = pabaiga->pirmyn;
        }
        naujas = calloc (1 , sizeof (struct dekas));
        naujas->duom = duom;
        naujas->atgal = pabaiga;
        pabaiga->pirmyn = naujas;
    }
    return 1;
}
int ideti_i_pradzia (dekas *pradzia , TIPAS duom){
    dekas naujas;
    if (ar_pilnas(*pradzia) == 1)
        return 0;
    if (ar_tuscias (*pradzia) == 1){
        (*pradzia) = calloc (1 , sizeof (struct dekas));
        (*pradzia)->duom = duom;
    }
    else{
        naujas = calloc (1 , sizeof (struct dekas));
        naujas->duom = duom;
        naujas->pirmyn = (*pradzia);
        (*pradzia)->atgal = naujas;
        (*pradzia) = naujas;
    }
    return 1;
}
int isimti_is_pabaigos (dekas *pradzia , TIPAS *duom){
    dekas pabaiga = (*pradzia);
    if (ar_tuscias (*pradzia) == 1)
        return 0;
    while(pabaiga->pirmyn != NULL)
        pabaiga = pabaiga->pirmyn;
    *duom = pabaiga->duom;
    if (pabaiga == (*pradzia)){
        free (pabaiga);
        (*pradzia) = NULL;
    }
    else{
    pabaiga = pabaiga->atgal;
    free (pabaiga->pirmyn);
    pabaiga->pirmyn = NULL;
    }
    return 1;
}
int isimti_is_pradzios (dekas *pradzia , TIPAS *duom){
    if (ar_tuscias (*pradzia) == 1)
        return 0;
    *duom = (*pradzia)->duom;
    if ( (*pradzia)->pirmyn == NULL){
        free (*pradzia);
        (*pradzia) = NULL;
    }
    else{
        (*pradzia) = (*pradzia)->pirmyn;
        free ( (*pradzia)->atgal);
        (*pradzia)->atgal = NULL;
    }
    return 1;
}
int ar_pilnas (dekas pradzia){
    dekas laikinas;
    if ((laikinas = malloc (sizeof (struct dekas))) == NULL)
        return 1;
    free (laikinas);
    return 0;
}
void sunaikinti (dekas *pradzia){
    if ( (*pradzia) != NULL){
        while ( (*pradzia)->pirmyn != NULL){
            (*pradzia) = (*pradzia)->pirmyn;
            free ( (*pradzia)->atgal);
        }
        free (*pradzia);
        (*pradzia) = NULL;
    }
}
