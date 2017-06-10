#ifndef DEKAS_H_INCLUDED
#define DEKAS_H_INCLUDED
#include "tipas_dekas.h"
struct dekas{
    TIPAS duom;
    struct dekas *atgal;
    struct dekas *pirmyn;
};
typedef struct dekas* dekas;
dekas naujas_tuscias_dekas ();
int ideti_i_pabaiga (dekas * , TIPAS);
int ideti_i_pradzia (dekas * , TIPAS);
int isimti_is_pabaigos (dekas * , TIPAS *);
int isimti_is_pradzios (dekas *, TIPAS *);
int ar_tuscias (dekas);
int ar_pilnas (dekas);
void sunaikinti (dekas *);
#endif // DEKAS_H_INCLUDED
