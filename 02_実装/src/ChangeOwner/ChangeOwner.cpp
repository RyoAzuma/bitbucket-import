#include <tc/tc.h>
#include <tc/emh.h>
#include <tccore/item.h>
#include <tccore/aom.h>
#include <sa/user.h>


int ITK_user_main(int argc, char* argv[])
{
   int status;
   char* usr = ITK_ask_cli_argument("-u=");/* gets user */
   char* upw = ITK_ask_cli_argument("-p=");/* gets the password */
   char* ugp = ITK_ask_cli_argument("-g=");/* what the group is */
   char* item_id = ITK_ask_cli_argument("-item=");/* アイテムID */
   char* ouser = ITK_ask_cli_argument("-ouser=");/* 変更後のユーザ */
   char *s;

   //TCログイン
   ITK_initialize_text_services (0);
   status = ITK_init_module( usr,upw,ugp);
   if ( status != ITK_ok)
   {
     EMH_ask_error_text( status, &s);
     printf("Error with ITK_init_module: %s \n",s);
     MEM_free(s);
     return status;
   }

   //アイテムTag取得
   tag_t itemTag;
   status = ITEM_find_item(item_id, &itemTag);
   if ( status != ITK_ok)
   {
     EMH_ask_error_text( status, &s);
     printf("Error with ITEM_find_item: %s \n",s);
     MEM_free(s);
     return status;
   }

   //変更後のユーザTag取得
   tag_t userTag;
   status = SA_find_user(ouser, &userTag);
   if ( status != ITK_ok)
   {
     EMH_ask_error_text( status, &s);
     printf("Error with SA_find_user: %s \n",s);
     MEM_free(s);
     return status;
   }

   //変更後のグループTag取得
   tag_t groupTag;
   status = SA_find_group("dba", &groupTag);
   if ( status != ITK_ok)
   {
     EMH_ask_error_text( status, &s);
     printf("Error with SA_find_group: %s \n",s);
     MEM_free(s);
     return status;
   }

   //所有権変更処理
   status = AOM_set_ownership(itemTag, userTag, groupTag);
   if ( status != ITK_ok)
   {
     EMH_ask_error_text( status, &s);
     printf("Error with AOM_set_ownership: %s \n",s);
     MEM_free(s);
     return status;
   }
   
   
   //TCログアウト
   ITK_exit_module( TRUE);
   return status;
}