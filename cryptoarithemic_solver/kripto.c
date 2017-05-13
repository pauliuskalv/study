# include <stdio.h>
# include <stdlib.h>
struct duomenys
{
    char raide ;
    int reiksme ;
    int paskirtas ;
    int nulis ;
} ;
int skaitliukas = 0 ;
int koks_ilgis ( char * eilute )
{
    int i = 0 ;
    for ( i ; eilute [ i ] != 0 ; i ++ ) ;
    return i ;
}
int ar_visi_paskirti ( struct duomenys * laik , int kiek )
{
    int i ;
    for ( i = 0 ; i < kiek ; i ++ )
    {
        if ( ( laik + i ) -> paskirtas == 0 )
        {
            return 0 ;
        }
    }
    return 1 ;
}
int kiek_argumentu ( char * eilute )
{
    int kiek = 0 , i ;
    for ( i = 0 ; eilute [ i ] != '=' ; i ++ )
    {
        if ( eilute [ i ] == '+' )
        {
            kiek ++ ;
        }
    }
    kiek ++ ;
    return kiek ;
}
int kiek_skirtingu_raidziu ( char * eilute )
{
    int i , kiek = 0 ;
    for ( i = 0 ; eilute [ i ] != 0 ; i ++ )
    {
        if ( eilute [ i ] == '+' || eilute [ i ] == '=' )
        {
            continue ;
        }
        kiek ++ ;
        int j ;
        for ( j = 0 ; j < i ; j ++ )
        {
            if ( eilute [ j ] == eilute [ i ] )
            {
                kiek -- ;
                break ;
            }
        }
    }
    return kiek ;
}
void isgauk_raides ( struct duomenys * laik , char * eilute )
{
    int i , j , galvute = 0 ;
    for ( i = 0 ; eilute [ i ] != 0 ; i ++ )
    {
        if ( eilute [ i ] == '+' || eilute [ i ] == '=' )
        {
            continue ;
        }
        int yra = 0 ;
        for ( j = 0 ; j < i ; j ++ )
        {
            if ( eilute [ i ] == eilute [ j ] )
            {
                yra = 1 ;
                break ;
            }
        }
        if ( ! yra )
        {
            ( laik + galvute ) -> raide = eilute [ i ] ;
            galvute ++ ;
        }
    }
}
int stumk_galvute ( char * eilute , int skaitliukas )
{
    for ( skaitliukas ; ; skaitliukas ++ )
    {
        if ( eilute [ skaitliukas ] == '+' || eilute [ skaitliukas ] == '=' )
        {
            skaitliukas ++ ;
            break ;
        }
    }
    return skaitliukas ;
}
char * isgauk_argumenta ( char * eilute , char * laik )
{
    int i ;
    for ( i = 0 ; ; i ++ )
    {
        if ( eilute [ i ] == '+' || eilute [ i ] == '=' || eilute [ i ] == 0 )
        {
            break ;
        }
        laik [ i ] = eilute [ i ] ;
    }
    laik [ i ] = 0 ;
    return laik ;
}
int kokia_reiksme ( char * argumentas , struct duomenys * laik )
{
    int temp = 0 , i , kiek , laikiklis = 1 ;
    for ( i = 0 ; argumentas [ i ] != 0 ; i ++ ) ;
    kiek = i -- ;
    for ( i = kiek ; i != -1 ; i -- )
    {
        int j ;
        for ( j = 0 ; ; j ++ )
        {
            if ( ( laik + j ) -> raide == argumentas [ i ] )
            {
                temp += laikiklis * ( laik + j ) -> reiksme ;
                break ;
            }
        }
        laikiklis *= 10 ;
    }
    return temp ;
}
int ar_tinka ( char * eilute , struct duomenys * laik )
{
    int pr = 0 , i , galvute = 0 , kiek = kiek_argumentu ( eilute ) ;
    char * argumentas = calloc ( koks_ilgis ( eilute ) , sizeof ( char ) ) ;
    for ( i = 0 ; i != kiek ; i ++ )
    {
        isgauk_argumenta ( eilute + galvute , argumentas ) ;
        pr += kokia_reiksme ( argumentas , laik ) ;
        galvute = stumk_galvute ( eilute , galvute ) ;
    }
    isgauk_argumenta ( eilute+galvute , argumentas ) ;
    int rez = kokia_reiksme ( argumentas , laik ) ;
    free ( argumentas ) ;
    if ( pr == rez )
    {
        return 1 ;
    }
    else
        return 0 ;
}
int skirk_reiksme ( struct duomenys * laik , int reiksme , int kiek , struct duomenys * kuris )
{
    int i ;
    for ( i = 0 ; i < kiek ; i ++ )
    {
        if ( ( laik + i ) -> reiksme == reiksme && ( laik + i ) -> paskirtas == 1 )
        {
            return 0 ;
        }
    }
    kuris -> reiksme = reiksme ;
    kuris -> paskirtas = 1 ;
    return 1 ;
}
int bandymas ( char * eilute , struct duomenys * laik , struct duomenys * pradzia , int kiek )
{
    if ( ar_visi_paskirti ( pradzia , kiek ) )
    {
        skaitliukas ++ ;
        return ar_tinka ( eilute , pradzia ) ;
    }
    int i ;
    if ( laik -> paskirtas == 1 )
    {
        if ( bandymas ( eilute , laik + 1 , pradzia , kiek ) )
        {
            return 1 ;
        }
        else
            return 0 ;
    }
    for ( i = 0 ; i <= 9 ; i ++ )
    {
        if ( laik -> nulis == 1 && i == 0 )
        {
            continue ;
        }
        if ( skirk_reiksme ( pradzia , i , kiek , laik ) )
        {
            if ( bandymas ( eilute , laik + 1 , pradzia , kiek ) )
            {
                return 1 ;
            }
            laik -> paskirtas = 0 ;
        }
    }
    return 0 ;
}
void ne_nuliai ( char * eilute , struct duomenys * laik )
{
    int kiek = koks_ilgis ( eilute ) ;
    int i ;
    int kiek_skir = kiek_skirtingu_raidziu ( eilute ) ;
    for ( i = 0 ; i < kiek ; i ++ )
    {
        if ( eilute [ i ] == '+' || eilute [ i ] == '=' )
        {
            int j ;
            for ( j = 0 ; j < kiek_skir ; j ++ )
            {
                if ( ( laik + j ) -> raide == eilute [ i + 1 ] )
                {
                    ( laik + j ) -> nulis = 1 ;
                    break ;
                }
            }
        }
        if ( i == 0 )
        {
            laik -> nulis = 1 ;
        }
    }
}
void ar_carry ( char * eilute , struct duomenys * laik , int * ar_galima )
{
    int i ;
    int arg_max = 0 , arg_kiek = 0 ;
    int galvute = 0 , kiek_arg = kiek_argumentu ( eilute ) ;
    char * argumentas = calloc ( koks_ilgis ( eilute ) , sizeof ( char ) ) ;
    for ( i = 0 ; i < kiek_arg ; i ++ )
    {
        argumentas = isgauk_argumenta ( eilute + galvute , argumentas ) ;
        galvute = stumk_galvute ( eilute , galvute ) ;
        if ( koks_ilgis ( argumentas ) > arg_max )
        {
            arg_max = koks_ilgis ( argumentas ) ;
            arg_kiek = 0 ;
            continue ;
        }
        if ( koks_ilgis ( argumentas ) == arg_max )
        {
            arg_kiek ++ ;
        }
    }
    int rez_ilgis = koks_ilgis ( ( eilute + galvute ) ) ;
    if ( arg_max + 1 < rez_ilgis || arg_max - 1 > rez_ilgis )
    {
        *ar_galima = 0 ;
        return 0 ;
    }
    if ( arg_max + 1 == rez_ilgis )
    {
        for ( i = 0 ; i < kiek_skirtingu_raidziu ( eilute ) ; i ++ )
        {
            if ( ( laik + i ) -> raide == *( eilute + galvute ) )
            {
                ( laik + i ) -> paskirtas = 1 ;
                ( laik + i ) -> reiksme = 1 ;
                break ;
            }
        }
    }
    int kur_rez = galvute ;
    galvute = 0 ;
    int temp = 0 , kur_nulis = 0 , kurio_nereikia = 0 , buvo_nulis = 0 ;
    for ( i = 0 ; i < kiek_argumentu ( eilute ) ; i ++ )
    {
        argumentas = isgauk_argumenta ( eilute + galvute , argumentas ) ;
        galvute = stumk_galvute ( eilute , galvute ) ;
        int k ;
        temp = koks_ilgis ( eilute + kur_rez ) - koks_ilgis ( argumentas ) ;
        for ( k = 0 ; k < koks_ilgis ( argumentas ) ; k ++ )
        {
            if ( *( eilute + kur_rez ) == argumentas [k] )
            {
                continue ;
            }
            if ( *( eilute + kur_rez + temp + k ) == argumentas [k] )
            {
                buvo_nulis = 1 ;
                int h , galvute_laik = 0 ;
                char * argumentas_laik = calloc ( koks_ilgis ( eilute ) , sizeof ( char ) ) ;
                for ( h = 0 ; h < kiek_argumentu ( eilute ) ; h ++ )
                {
                    if ( h == i )
                    {
                        continue ;
                    }
                    argumentas_laik = isgauk_argumenta ( eilute + galvute_laik , argumentas_laik ) ;
                    galvute_laik = stumk_galvute ( eilute , galvute_laik ) ;
                    if ( k > koks_ilgis(argumentas_laik) )
                    {
                        continue ;
                    }
                    int g ;
                    for ( g = 0 ; g < kiek_skirtingu_raidziu ( eilute ) ; g ++ )
                    {
                        if ( ( laik + g ) -> raide == *( eilute + galvute_laik + k ) && ( laik + g ) -> paskirtas != 1 && ( laik + g ) -> nulis != 1 )
                        {
                            ( laik + g ) -> paskirtas = 1 ;
                            ( laik + g ) -> reiksme = 0 ;
                            free ( argumentas_laik ) ;
                            return 1 ;
                        }
                    }
                }
                free ( argumentas_laik ) ;
            }
        }
    }
}
void kripto_main ( char * eilute )
{
    printf ( "%s\n" , eilute ) ;
    int kiek = kiek_skirtingu_raidziu ( eilute ) ;
    if ( kiek > 10 )
    {
        printf ( "Sakinyje daugiau skaiciu negu desimtaineje sistemoje\n" ) ;
        return ;
    }
    int i , ar_galima = 1 ;
    skaitliukas = 0 ;
    struct duomenys * laik = calloc ( kiek , sizeof ( struct duomenys ) ) ;
    isgauk_raides ( laik , eilute ) ;
    ne_nuliai ( eilute , laik ) ;
    ar_carry ( eilute , laik , &ar_galima ) ;
    if ( ! ar_galima )
    {
        printf ( "Negalima tokio sakinio isspresti\n\n" ) ;
        return 0 ;
    }
    bandymas ( eilute , laik , laik , kiek ) ;
    if ( ! ar_visi_paskirti ( laik , kiek ) )
    {
        printf ( "Nera jokio teisingo atsakymo\n\n" ) ;
        free ( laik ) ;
        return 0 ;
    }
    for ( i = 0 ; i < koks_ilgis ( eilute ) ; i ++ )
    {
        int j ;
        if ( eilute [ i ] == '+' || eilute [ i ] == '=' )
        {
            printf ( "%c" , eilute [ i ] ) ;
        }
        for ( j = 0 ; j < kiek_skirtingu_raidziu ( eilute ) ; j ++ )
        {
            if ( ( laik + j ) -> raide == eilute [ i ] )
            {
                printf ( "%d" , ( laik + j ) -> reiksme ) ;
            }
        }
    }
    printf ( "\n" ) ;
    for ( i = 0 ; i < kiek ; i ++ )
    {
        printf ( "%c\t%d\n" , laik [ i ] . raide , laik [ i ] . reiksme ) ;
    }
    printf ( "Is viso bandymu: %d\n\n" , skaitliukas ) ;
    free ( laik ) ;
}
