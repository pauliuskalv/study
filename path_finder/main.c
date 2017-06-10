#include <stdio.h>
#include <stdlib.h>
#include "functions.c"
#define MIESTO_ILGIS 32 // ilgis simboliais
int main()
{
    FILE * duom = fopen( "duom.txt" , "r" ) ;
    if ( duom == NULL )
    {
        printf ( "Duomenu failas nerastas!\n" ) ;
        return 0 ;
    }
    char * buferis = calloc ( 256 , sizeof ( char ) ) ;
    if ( ! fgets ( buferis , 256 , duom ) )
    {
        printf ( "Duomenu failas tuscias!\n" ) ;
        return 0 ;
    }
    int miestu_kiekis = 2 ;
    char ** miestai = calloc ( 256 , sizeof ( char * ) ) ;
    miestai [ 0 ] = calloc ( MIESTO_ILGIS , sizeof ( char ) ) ;
    miestai [ 1 ] = calloc ( MIESTO_ILGIS , sizeof ( char ) ) ;
    cpystr ( buferis , miestai [ 0 ] , 1 ) ;
    cpystr ( buferis , miestai [ 1 ] , 2 ) ;
    char * laik = calloc ( MIESTO_ILGIS , sizeof ( char ) ) ;
    for ( ; fgets ( buferis , 256 , duom ) ; )
    {
        int j ;
        cpystr ( buferis , laik , 1 ) ;
        if ( ! is_in_graph ( miestai , laik , miestu_kiekis ) )
        {
            miestai [ miestu_kiekis ] = calloc ( MIESTO_ILGIS , sizeof ( char ) ) ;
            cpystr ( buferis , miestai [ miestu_kiekis ] , 1 ) ;
            miestu_kiekis ++ ;
        }
        cpystr ( buferis , laik , 2 ) ;
        if ( ! is_in_graph ( miestai , laik , miestu_kiekis ) )
        {
            miestai [ miestu_kiekis ] = calloc ( MIESTO_ILGIS , sizeof ( char ) ) ;
            cpystr ( buferis , miestai [ miestu_kiekis ] , 2 ) ;
            miestu_kiekis ++ ;
        }
    }
    free ( buferis ) ;
    int ** values = create ( miestai , miestu_kiekis , duom ) ;
    find_path ( miestai , values , miestu_kiekis ) ;
    delete ( & values , miestu_kiekis ) ;
    return 1;
}
