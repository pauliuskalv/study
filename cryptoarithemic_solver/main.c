#include <stdio.h>
#include <stdlib.h>
#define DYDIS 256
#include "kripto.h"

int main()
{
    /*
    * Kai kurie sakiniai imti is http://bach.istc.kobe-u.ac.jp/llp/crypt.html
    */
    kripto_main ( "KUMAMOTO+TOTTORI=WAKAYAMA" ) ;
    kripto_main ( "SEND+MORE=MONEY" ) ;
    kripto_main ( "OITA+TOYAMA=NAGOYA" ) ;
    kripto_main ( "BASE+BALL=GAMES" ) ;
    kripto_main ( "AAA+CCC=CCC" ) ;
    kripto_main ( "AB+CD=EFGH" ) ;
    kripto_main ( "CRASH+HACKER=MEMORY" ) ;
    kripto_main ( "MONITOR+NETWORK=INTERNET" ) ;
    kripto_main ( "APPLE+BUG+GUI=IBOOK" ) ;
    kripto_main ( "C+ADA+SCALA=COBOL" ) ;
    kripto_main ( "C+ALGOL+LOGIC=SIMULA" ) ;
    kripto_main ( "CRACK+HACK=MEMORY" ) ;
    return 1 ;
}
