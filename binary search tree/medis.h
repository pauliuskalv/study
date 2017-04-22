# ifndef MEDIS_INCLUDED
# define MEDIS_INCLUDED
# include "medis_tipas.h"
typedef struct root * medis ;
struct root ;
/**
*Funkcija kurk_medi sukuria dvejetaini paieskos medi pagal 3 parametrus:
*reiksme [] - tai masyvas, kuriame laikomi duomenys, kuriuos ideti i pradini medi.
*kiek - tai kiek elementu yra is viso perduotame masyve ( PASTABA! Jeigu masyvas paskelbtas array [N], tai jame N elementu ).
*pradzia - tai nuo kurio elemento pradeti deti elementus i medi. Elementai po to iterpiami vienas po kito, pradedant pirmu is masyvo.
*Funkcija grazina rodykle i naujai sukurta medi, todel rekomenduotina prilyginti funkcija jau sukurtam medis tipo kintamajam.
*/
medis kurk_medi ( reiksme [] , int kiek , int pradzia ) ;
/**
*Funkcija kurk_tuscia_medi sukuria dvejetaini paieskos medi, kuriame lygiai 0 elementu.
*Funkcija grazina rodykle i naujai sukurta medi, todel rekomenduotina prilyginti funkcija jau sukurtam medis tipo kintamajam.
*/
medis kurk_tuscia_medi ( ) ;
/**
*Funkcija spausdink_medi isspausdina pasirinkta medi.
*Funkcija negrazina jokiu reiksmiu.
*TIPAS nurodo spausdink_medi funkcijai kokiu formatu spausdinti kintamuosius, esancius medyje.
*TIPAS = 'f' , jeigu norima spausdinti slankaus kablelio formatu
*TIPAS = 'd' , jeigu norima spausdinti sveiko skaiciaus su zenklu formatu desimtaine forma.
*TIPAS = 'c' , jeigu norima spausdinti simbolio formatu.
*TIPAS = 'x' , jeigu norima spausdinti sesioliktainiu formatu.
*TIPAS = 'u' , jeigu norima spausdinti sveiko skaiciaus be zenklo formatu desimtaine forma.
*/
void spausdink_medi ( medis , char TIPAS ) ;
/**
*Funkcija iterpk iterpia duota elementa i jau sukurta dvejetaini paieskos medi.
*reiksme - tai elementas, kuri norima iterpti i medi.
*medis - tai jau anksciau sukurtas dvejetainis paieskos medis.
*Jeigu iterpimas pavyko, funkcija grazina 1. Kitu atveju - 0.
*/
int iterpk ( medis , reiksme ) ;
/**
*Funkcija balansuok subalansuoja duota medi.
*medis - tai anksciau sukurtas dvejetainis paieskos medis.
*Funkcija grazina rodykle i subalansuota medi, todel rekomenduotina prilyginti funkcija jau sukurtam medis tipo kintamajam.
*/
medis balansuok ( medis ) ;
/**
*Funkcija istrink istrina tam tikra reiksme is duoto dvejetainio paieskos medzio.
*reiksme - tai elementas, kuri siekiama istrinti.
*medis - tai anksciau sukurtas dvejetainis paieskos medis.
*Jeigu istrynimas pavyko, funkcija grazina 1. Kitu atveju - 0.
*/
int istrink ( medis , reiksme ) ;
/**
*Funkcija istrink_medi pilnai istrina dvejetaini paieskos medi.
*Jeigu istrynimas pavyksta, tai funkcija grazina 1. Kitu atveju - 0.
*Sekmingai ivykdzius funkcija, rekomenduotina jam perduota kintamaji prilyginti 0.
*/
int istrink_medi ( medis ) ;
/**
*Funkcija medzio_aukstis grazina skaiciu, kuris nusako duotojo medzio auksti.
*Grazina 0, jeigu medis dar nesukurtas arba tuscias.
*/
int medzio_aukstis ( medis ) ;
/**
*Funkcija ar_egzistuoja grazina skaiciu, kuris nusako ar tam tikra reiksme egzistuoja duotame medyje.
*Jeigu reiksme egzistuoja medyje, grazina kiek zingsniu reikejo atlikti keliaujant medziu, kad nueiti iki jos.
*Jeigu reiksme neegzistuoja medyje, grazina 0.
*/
int ar_egzistuoja ( medis , reiksme ) ;
/**
*Funkcija ar_tuscias grazina 1, jeigu medis nera tuscias dvejetainis paieskos medis.
*Grazina 0, jeigu dvejetainis paieskos medis tuscias.
*/
int ar_tuscias ( medis ) ;
/**
*Funkcija ar_pilnas grazina 1, jeigu kompiuterio atmintyje uztenka vietos naujam elementui iterpti i medi.
*medis - tai jau sukurtas dvejetainis paieskos medis.
*Grazina 0, jeigu vietos pritruktu naujo elemento iterpimui.
*/
int ar_pilnas ( medis ) ;
/**
*Funkcija kiek_medyje_elementu grazina skaiciu, kuris nusako kiek is viso yra elementu medyje tam tikru momentu.
*Jeigu medis tuscias, grazina 0.
*/
int kiek_medyje_elementu ( medis ) ;
# endif // MEDIS_INCLUDED
