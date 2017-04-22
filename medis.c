# include <stdlib.h>
# include <stdio.h>
# include "medis.h"
struct root
{
    reiksme data ;
    struct root * kaire ;
    struct root * desine ;
} ;
struct root * kurk_bal_medi ( reiksme duom [] , int pradzia , int pabaiga )
{
    if ( pradzia > pabaiga )
    {
        return 0 ;
    }
    struct root * temp = malloc ( sizeof ( struct root ) ) ;
    int vidurys = ( pradzia + pabaiga ) / 2 ;
    temp -> data = duom [ vidurys ] ;
    temp -> kaire = kurk_bal_medi ( duom , pradzia , vidurys - 1 ) ;
    temp -> desine = kurk_bal_medi ( duom , vidurys + 1 , pabaiga ) ;
    return temp ;
}
medis kurk_medi ( reiksme data [ ] , int kiek, int vidurys )
{
    if ( vidurys >= kiek )
    {
        return 0 ;
    }
    medis laik = malloc ( sizeof ( struct root ) ) ;
    laik -> data = data [ vidurys ] ;
    medis galvute = laik ;
    int i = 0 ;
    while ( 1 )
    {
        if ( i == kiek )
        {
            break ;
        }
        if ( i == vidurys )
        {
            i ++ ;
            continue ;
        }
        if ( i != vidurys )
        {
            if ( galvute -> data > data [ i ] )
            {
                if ( galvute -> kaire == NULL )
                {
                    galvute -> kaire = malloc ( sizeof ( struct root ) ) ;
                    ( galvute -> kaire ) -> data = data [ i ] ;
                    galvute = laik ;
                    i ++ ;
                    continue ;
                }
                else
                    galvute = galvute -> kaire ;
                continue ;
            }
            if ( galvute -> data < data [ i ] )
            {
                if ( galvute -> desine == NULL )
                {
                    galvute -> desine = malloc ( sizeof ( struct root ) ) ;
                    ( galvute -> desine ) -> data = data [ i ] ;
                    galvute = laik ;
                    i ++ ;
                    continue ;
                }
                else
                    galvute = galvute -> desine ;
                continue ;
            }
        }
    }
    return laik ;
}
int kiek_medyje ( medis medis , int skaitliukas )
{
    if ( medis == NULL )
    {
        return skaitliukas ;
    }
    skaitliukas ++ ;
    skaitliukas = kiek_medyje ( medis -> kaire , skaitliukas ) ;
    skaitliukas = kiek_medyje ( medis -> desine , skaitliukas ) ;
    return skaitliukas ;
}
void spausdink ( medis medis , char TIPAS )
{
    if ( medis == NULL )
    {
        return ;
    }
    printf ( "Adresas: %x\tKaire: %x\tDesine: %x\tDuomenys: ", medis, medis -> kaire , medis -> desine ) ;
    if ( TIPAS == 'c' )
    {
        printf ( "%c\n" , medis -> data ) ;
    }
    else
        if ( TIPAS == 'd' )
    {
        printf ( "%d\n" , medis -> data ) ;
    }
    else
        if ( TIPAS == 'f' )
    {
        printf ( "%f\n" , medis -> data ) ;
    }
    else
        if ( TIPAS == 'u' )
    {
        printf ( "%u\n" , medis -> data ) ;
    }
    else
        if ( TIPAS == 'x' )
    {
        printf ( "%x\n" , medis -> data ) ;
    }
    spausdink ( medis -> kaire , TIPAS ) ;
    spausdink ( medis -> desine , TIPAS ) ;
}
int isgauk_duomenis ( medis medis , reiksme * data , int kuris )
{
    if ( medis == NULL )
    {
        return kuris ;
    }
    data [ kuris ] = medis -> data ;
    kuris ++ ;
    kuris = isgauk_duomenis ( medis -> kaire , data , kuris ) ;
    kuris = isgauk_duomenis ( medis -> desine , data , kuris ) ;
    return kuris ;
}
int istrink_medi ( medis medis_laik )
{
    if ( medis_laik == NULL )
    {
        return 0 ;
    }
    if ( ar_tuscias ( medis_laik ) )
    {
        free ( medis_laik ) ;
        return 1 ;
    }
    istrink_medi ( medis_laik -> kaire ) ;
    istrink_medi ( medis_laik -> desine ) ;
    free ( medis_laik ) ;
    return 1 ;
}
medis balansuok ( medis medis )
{
    if ( medis == NULL )
    {
        return 0 ;
    }
    if ( ar_tuscias ( medis ) )
    {
        return 0 ;
    }
    int skaitliukas = 0 ;
    skaitliukas = kiek_medyje ( medis , skaitliukas ) ;
    reiksme * laikinas = calloc ( skaitliukas , sizeof ( reiksme ) ) ;
    isgauk_duomenis(medis,laikinas,0) ;
    istrink_medi (medis) ;
    int i , j ;
    for ( i = 0 ; i < skaitliukas ; i ++ )
    {
        for ( j = 0 ; j < skaitliukas ; j ++ )
        {
            if ( laikinas [ i ] < laikinas [ j ] )
            {
                reiksme temp = laikinas [ i ] ;
                laikinas [ i ] = laikinas [ j ] ;
                laikinas [ j ] = temp ;
            }
        }
    }
    medis = kurk_bal_medi ( laikinas , 0 , skaitliukas - 1 ) ;
    free ( laikinas ) ;
    return medis ;
}
void spausdink_medi ( medis medis_laik , char TIPAS )
{
    if ( medis_laik == NULL )
    {
        printf ( "Medis dar nesukurtas!\n") ;
        return ;
    }
    if ( ar_tuscias ( medis_laik ) )
    {
        printf ( "Medis tuscias!\n" ) ;
        return ;
    }
    if ( TIPAS != 'c' && TIPAS != 'd' && TIPAS != 'x' && TIPAS != 'u' && TIPAS != 'f' )
    {
        printf ( "Neatpazintas spausdinimo formatas!\n" ) ;
        return ;
    }
    printf( "\n" ) ;
    printf ( "--------------------------------------------------------------------------------\n" ) ;
    printf ( "Pradzia:\n" ) ;
    printf ( "Adresas: %x\tKaire: %x\tDesine: %x\tDuomenys: " , medis_laik , medis_laik -> kaire , medis_laik -> desine ) ;
    if ( TIPAS == 'c' )
    {
        printf ( "%c\n" , medis_laik -> data ) ;
    }
    else
        if ( TIPAS == 'd' )
    {
        printf ( "%d\n" , medis_laik -> data ) ;
    }
    else
        if ( TIPAS == 'f' )
    {
        printf ( "%f\n" , medis_laik -> data) ;
    }
    else
        if ( TIPAS == 'u' )
    {
        printf ( "%u\n" , medis_laik -> data ) ;
    }
    else
        if ( TIPAS == 'x' )
    {
        printf ( "%x\n" , medis_laik -> data ) ;
    }
    if ( medis_laik -> kaire != NULL )
    {
        printf ( "================================================================================\n" ) ;
        printf ( "Kairysis pomedis:\n" ) ;
        spausdink ( medis_laik -> kaire , TIPAS ) ;
    }
    if ( medis_laik -> desine != NULL )
    {
        printf ( "================================================================================\n" ) ;
        printf ( "Desinysis pomedis:\n" ) ;
        spausdink ( medis_laik -> desine , TIPAS ) ;
    }
    printf ( "--------------------------------------------------------------------------------\n" ) ;
    printf("\n");
    return ;
}
int ar_egzistuoja ( medis medis_laik , reiksme data )
{
    if ( medis_laik == NULL )
    {
        return 0 ;
    }
    if ( ar_tuscias ( medis_laik ) )
    {
        return 0 ;
    }
    int skaitliukas = 1 ;
    medis galvute = medis_laik ;
    while ( 1 )
    {
        if ( galvute != NULL )
        {
            if ( galvute -> data == data )
            {
                return skaitliukas ;
            }
            if ( galvute -> data < data )
            {
                galvute = galvute -> desine ;
                skaitliukas ++ ;
                continue ;
            }
            if ( galvute -> data > data )
            {
                galvute = galvute -> kaire ;
                skaitliukas ++ ;
                continue ;
            }
        }
        else
            return 0 ;
    }
}
int istrink ( medis medis_laik , reiksme data )
{
    if ( medis_laik == NULL )
    {
        return 0 ;
    }
    if ( ar_tuscias ( medis_laik ) )
    {
        return 0 ;
    }
    medis galvute = medis_laik ;
    medis laik = NULL ;
    if ( ar_egzistuoja ( medis_laik , data ) )
    {
        if ( medis_laik -> kaire == NULL && medis_laik -> desine == NULL )
        {
            medis_laik -> data = 0 ;
            medis_laik -> kaire = 1 ;
            medis_laik -> desine = 1 ;
            return 2 ;
        }
        while ( 1 )
        {
            if ( galvute -> data > data )
            {
                if ( ( galvute -> kaire ) -> data == data )
                {
                    if ( ( galvute -> kaire ) -> kaire == NULL && ( galvute -> kaire ) -> desine == NULL )
                    {
                        free ( galvute -> kaire ) ;
                        galvute -> kaire = NULL ;
                        return 1 ;
                    }
                    else
                        {
                            laik = galvute -> kaire ;
                            break ;
                        }
                }
                else
                    galvute = galvute -> kaire ;
            }
            if ( galvute -> data < data )
            {
                if ( ( galvute -> desine ) -> data == data )
                {
                    if ( ( galvute -> desine ) -> kaire == NULL && ( galvute -> desine ) -> desine == NULL )
                    {
                        free ( galvute -> desine ) ;
                        galvute -> desine = NULL ;
                        return 1 ;
                    }
                    else
                    {
                        laik = galvute -> desine ;
                        break ;
                    }
                }
                else
                    galvute = galvute -> desine ;
            }
            if ( galvute -> data == data )
            {
                if ( galvute -> kaire == NULL && galvute -> desine == NULL )
                {
                    galvute -> data = 0 ;
                    return 2 ;
                }
                else
                    laik = galvute ;
                break ;
            }
        }
    }
    else
        return 0 ;
    galvute = laik ;
    if ( galvute -> kaire != NULL )
    {
        if ( ( galvute -> kaire ) -> kaire == NULL && ( galvute -> kaire ) -> desine == NULL )
        {
            laik -> data = ( galvute -> kaire ) -> data ;
            free ( galvute -> kaire ) ;
            galvute -> kaire = NULL ;
            return 1 ;
        }
        galvute = galvute -> kaire ;
        while ( 1 )
        {
            if ( ( galvute -> desine ) -> kaire == NULL && ( galvute -> desine ) -> desine == NULL )
            {
                laik -> data = ( galvute -> desine ) -> data ;
                free ( galvute -> desine ) ;
                galvute -> desine = NULL ;
                return 1 ;
            }
            if ( (galvute -> desine ) -> desine == NULL && ( galvute -> desine ) -> kaire != NULL )
            {
                laik -> data = ( galvute -> desine ) -> data ;
                medis temp = galvute -> desine ;
                galvute -> desine = ( galvute -> desine ) -> kaire ;
                free ( temp ) ;
                return 1 ;
            }
            galvute = galvute -> desine ;
        }
    }
    else
    {
        if ( ( galvute -> desine ) -> kaire == NULL && ( galvute -> desine ) -> desine == NULL )
        {
            laik -> data = ( galvute -> desine ) -> data ;
            free ( galvute -> desine ) ;
            galvute -> desine = NULL ;
            return 1 ;
        }
        galvute = galvute -> desine ;
        while ( 1 )
        {
            if ( ( galvute -> kaire ) -> kaire == NULL && ( galvute -> kaire ) -> desine == NULL )
            {
                laik -> data = ( galvute -> kaire ) -> data ;
                free ( galvute -> kaire ) ;
                galvute -> kaire = NULL ;
                return 1 ;
            }
            if ( (galvute -> kaire ) -> desine != NULL && ( galvute -> kaire ) -> kaire == NULL )
            {
                laik -> data = ( galvute -> kaire ) -> data ;
                medis temp = galvute -> kaire ;
                galvute -> desine = ( galvute -> kaire ) -> desine ;
                free ( temp ) ;
                return 1 ;
            }
            galvute = galvute -> kaire ;
        }
    }
}
int iterpk ( medis medis_laik , reiksme data )
{
    if ( medis_laik == NULL )
    {
        return 0 ;
    }
    if ( !ar_egzistuoja ( medis_laik , data ) )
    {
        medis galvute = medis_laik ;
        if ( ar_tuscias ( medis_laik ) )
        {
            medis_laik -> data = data ;
            medis_laik -> desine = 0 ;
            medis_laik -> kaire = 0 ;
            return 1 ;
        }
        while ( 1 )
        {
            if ( galvute -> data > data )
            {
                if ( galvute -> kaire == NULL )
                {
                    galvute -> kaire = malloc ( sizeof ( struct root ) ) ;
                    ( galvute -> kaire ) -> data = data ;
                    return 1 ;
                }
                else
                    galvute = galvute -> kaire ;
            }
            else
            {
                if ( galvute -> desine == NULL )
                {
                    galvute -> desine = malloc ( sizeof ( struct root ) ) ;
                    ( galvute -> desine ) -> data = data ;
                    return 1 ;
                }
                else
                    galvute = galvute -> desine ;
            }
        }
    }
    else
        return 0 ;
}
medis kurk_tuscia_medi ( )
{
    medis laik = malloc ( sizeof ( struct root ) ) ;
    laik -> kaire = 1 ;
    laik -> desine = 1 ;
    return laik ;
}
int ar_tuscias ( medis medis_laik )
{
    if ( medis_laik == NULL )
    {
        return 1 ;
    }
    if ( medis_laik -> kaire == 1 && medis_laik -> desine == 1 )
    {
        return 1 ;
    }
    else
        return 0 ;
}
int medzio_aukstis ( medis medis_laik )
{
    if ( ar_tuscias ( medis_laik ) )
    {
        return 0 ;
    }
    int skaitliukas = kiek_medyje ( medis_laik , 0 ) ;
    reiksme * laikinas = calloc ( skaitliukas , sizeof ( reiksme ) ) ;
    isgauk_duomenis ( medis_laik , laikinas , 0) ;
    medis galvute = medis_laik ;
    int i = 0 , aukstis = 1 , aukstis_laik = 1 ;
    while ( 1 )
    {
        if ( i == skaitliukas )
        {
            break ;
        }
        if ( aukstis_laik > aukstis )
        {
            aukstis = aukstis_laik ;
        }
        if ( galvute -> data == laikinas [ i ] )
        {
            aukstis_laik = 1 ;
            galvute = medis_laik ;
            i ++ ;
            continue ;
        }
        if ( galvute -> data > laikinas [ i ] )
        {
            galvute = galvute -> kaire ;
            aukstis_laik ++ ;
            continue ;
        }
        if ( galvute -> data < laikinas [ i ] )
        {
            galvute = galvute -> desine ;
            aukstis_laik ++ ;
            continue ;
        }
    }
    free ( laikinas ) ;
    return aukstis ;
}
int ar_pilnas ( medis laik_1 )
{
    medis laik = malloc ( sizeof ( medis ) ) ;
    if ( laik )
    {
        free ( laik ) ;
        return 0 ;
    }
    return 1 ;
}
int kiek_medyje_elementu ( medis medis_laik )
{
    if ( ar_tuscias ( medis_laik ) )
    {
        return 0 ;
    }
    return kiek_medyje ( medis_laik , 0 ) ;
}
