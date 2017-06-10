#include <stdio.h>
#include <stdlib.h>
#include "tipas_dekas.h"
#include "dekas.c"
int kelias ;
int kiekis ;
int find ( int from , int to , char ** cities , int ** values , int quantity , int ** visited , int min , dekas * route ) ;
void find_quantity ( int from , int to , char ** cities , int ** values , int quantity , int ** visited ) ;
void find_min ( int from , int to , char ** cities , int ** values , int quantity , int ** visited , int * min ) ;
int cmp ( char * pirmas , char * antras )
{
    int i = 0 ;
    for ( i ; ; i ++ )
    {
        if ( pirmas [i] != antras [i] )
        {
            return 0 ;
        }
        if ( pirmas [i] == 0 && antras [i] == 0 )
        {
            return 1 ;
        }
    }
}
void cpystr ( char * from , char * to , int which )
{
    int i = 0 ; // used as an offset
    if ( which == 2 )
    {
        for ( i ; from [i] != ' ' ; i ++ ) ;
        i ++ ;
    }
    int j ;
    for ( j = 0 ; from [ i + j ] != ' ' ; j ++ )
    {
        * ( to + j) = from [ i + j ] ;
    }
    * ( to + j ) = 0 ;
}
int skaityk_skaiciu ( char * buffer )
{
    int i , j = 1 , laik = 0 ;
    for ( i = 0 ; buffer [ i ] != 0 ; i ++ ) ;
    for ( i ; buffer [ i ] != ' ' ; i -- )
    {
        switch ( buffer [ i ] )
        {
            case '0' : laik += j * 0 ; j *= 10 ; break ;
            case '1' : laik += j * 1 ; j *= 10 ; break ;
            case '2' : laik += j * 2 ; j *= 10 ; break ;
            case '3' : laik += j * 3 ; j *= 10 ; break ;
            case '4' : laik += j * 4 ; j *= 10 ; break ;
            case '5' : laik += j * 5 ; j *= 10 ; break ;
            case '6' : laik += j * 6 ; j *= 10 ; break ;
            case '7' : laik += j * 7 ; j *= 10 ; break ;
            case '8' : laik += j * 8 ; j *= 10 ; break ;
            case '9' : laik += j * 9 ; j *= 10 ; break ;
            default : break ;
        }
    }
    return laik ;
}
void cpy_str ( char * from , char ** to )
{
    int i ;
    for ( i = 0 ; ; i ++ )
    {
        if ( from [ i ] == 0 )
        {
            break ;
        }
        *( * to + i ) = from [ i ] ;
    }
}
int is_in_graph ( char ** miestai , char * miestas , int quantity )
{
    int i ;
    for ( i = 0 ; i < quantity ; i ++ )
    {
        if ( cmp ( miestai [ i ] , miestas ) )
        {
            return 1 ;
        }
    }
    return 0 ;
}
int ** create ( char ** miestai , int quantity , FILE * duom )
{
    int ** values = calloc ( quantity , sizeof ( int * ) * quantity ) ;
    int i ;
    for ( i = 0 ; i < quantity ; i ++ )
    {
        * ( values + i ) = calloc ( quantity , sizeof ( int ) ) ;
    }
    rewind ( duom ) ;
    char * buferis = calloc ( 256 , sizeof ( char ) ) ;
    char * name_1 = calloc ( 256 , sizeof ( char ) ) ;
    char * name_2 = calloc ( 256 , sizeof ( char ) ) ;
    for ( ; fgets ( buferis , 256 , duom ) ; )
    {
        cpystr ( buferis , name_1 , 1 ) ;
        cpystr ( buferis , name_2 , 2 ) ;
        int i ;
        for ( i = 0 ; i < quantity ; i ++ )
        {
            if ( cmp ( miestai [ i ] , name_1 ) )
            {
                int j ;
                for ( j = 0 ; j < quantity ; j ++ )
                {
                    if ( cmp ( miestai [ j ] , name_2 ) )
                    {
                        int path = skaityk_skaiciu ( buferis ) ;
                        values [ i ] [ j ] = path ;
                        values [ j ] [ i ] = path ;
                        break ;
                    }
                }
                break ;
            }
        }
    }
    return values ;
}
int find_path ( char ** cities , int ** values , int quantity )
{
    int * laik = calloc ( quantity , sizeof ( int ) ) ;
    int i , j ;
    char * city = calloc ( 256 , sizeof ( char ) ) ;
    printf ( "Enter the city's name, from which You are trying to travel:\n" ) ;
    scanf ( "%s" , city ) ;
    for ( i = 0 ; i <= quantity ; i ++ )
    {
        if ( i == quantity )
        {
            i = -1 ;
            break ;
        }
        if ( cmp ( cities [ i ] , city ) )
        {
            break ;
        }
    }
    if ( i == -1 )
    {
        free ( laik ) ;
        printf ( "The city, from which you are trying to travel, does not exist!\n" ) ;
        return 0 ;
    }
    printf ( "Enter the city's name, to which you are trying to travel:\n" ) ;
    scanf ( "%s" , city ) ;
    for ( j = 0 ; j <= quantity ; j ++ )
    {
        if ( j == quantity )
        {
            j = -1 ;
            break ;
        }
        if ( cmp ( cities [ j ] , city ) )
        {
            break ;
        }
    }
    if ( j == -1 )
    {
        free ( laik ) ;
        printf ( "The city, to which You are trying to travel, does not exist!\n" ) ;
        return 0 ;
    }
    kiekis = 0 , kelias = 0 ;
    find_quantity ( i , j , cities , values , quantity , & laik ) ;
    if ( kiekis == 0 )
    {
        printf ( "There is no possible route!\n" ) ;
        return 0 ;
    }
    int * temp = calloc ( kiekis , sizeof ( int ) ) ;
    kiekis = 0 ;
    find_min ( i , j , cities , values , quantity , & laik , temp ) ;
    int k , min = temp [ 0 ] ;
    for ( k = 1 ; k < kiekis ; k ++ )
    {
        if ( temp [ k ] < min )
        {
            min = temp [ k ] ;
        }
    }
    dekas route = naujas_tuscias_dekas ( ) ;
    find ( i , j , cities , values , quantity , & laik , min , & route ) ;
    j = 0 ;
    while ( 1 )
    {
        if ( ! isimti_is_pabaigos ( & route , & i ) )
        {
            break ;
        }
        if ( j == 0 )
        {
            printf ( "%s " , cities [ i ] ) ;
            j ++ ;
            continue ;
        }
        printf ( "-> %s " , cities [ i ] ) ;
    }
    printf ( "\nTotal calculated distance: %d\n" , min ) ;
    free ( laik ) ;
    free ( temp ) ;
    return 1 ;
}
void find_quantity ( int from , int to , char ** cities , int ** values , int quantity , int ** visited )
{
    * ( *visited + from ) = 1 ;
    if ( from == to )
    {
        kiekis ++ ;
    }
    int i ;
    for ( i = 0 ; i < quantity ; i ++ )
    {
        if ( values [ from ] [ i ] != 0 && *( *visited + i ) == 0 )
        {
            find_quantity ( i , to , cities , values , quantity , visited ) ;
        }
    }
    * ( *visited + from ) = 0 ;
    return ;
}
void find_min ( int from , int to , char ** cities , int ** values , int quantity , int ** visited , int * min )
{
    * ( * visited + from ) = 1 ;
    if ( from == to )
    {
        min [ kiekis ] = kelias ;
        kiekis ++ ;
    }
    int i ;
    for ( i = 0 ; i < quantity ; i ++ )
    {
        if ( values [ from ] [ i ] != 0 && * ( * visited + i ) == 0 )
        {
            kelias += values [ from ] [ i ] ;
            find_min ( i , to , cities , values , quantity , visited , min ) ;
            kelias -= values [ from ] [ i ] ;
        }
    }
    * ( * visited + from ) = 0 ;
    return ;
}
int find ( int from , int to , char ** cities , int ** values , int quantity , int ** visited , int min , dekas * route )
{
    * ( * visited + from ) = 1 ;
    if ( from == to && min == kelias )
    {
        ideti_i_pabaiga ( route , from ) ;
        return 1 ;
    }
    int i ;
    for ( i = 0 ; i < quantity ; i ++ )
    {
        if ( values [ from ] [ i ] != 0 && * ( * visited + i ) == 0 )
        {
            kelias += values [ from ] [ i ] ;
            if ( find ( i , to , cities , values , quantity , visited , min , route ) )
            {
                ideti_i_pabaiga ( route , from ) ;
                return 1 ;
            }
            kelias -= values [ from ] [ i ] ;
        }
    }
    * ( * visited + from ) = 0 ;
    return 0 ;
}
void delete ( int *** values , int quantity )
{
    int j ;
    for ( j = 0 ; j < quantity ; j ++ )
    {
        free ( * ( * values + j ) ) ;
    }
    free ( **values ) ;
    *values = NULL ;
}