/*
 * file:        shell3650.c
 * description: skeleton code for a simple shell
 *
 * 
 */

/* <> means don't check the local directory */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <stdbool.h>

/* "" means check the local directory */
#include "parser.h"

/* you'll need these includes later: */
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <fcntl.h>

#include <limits.h>

/*int cd(n_tokens, tokens) argc argv */

int currexitstatus = 0;

int cd(int n_tokens, char* tokens[]){
       if(n_tokens == 1) {
       if (chdir(getenv("HOME")) == -1) {
         fprintf(stderr, "1cd: %s\n", strerror(errno));
            currexitstatus = 1;
            return 1;
        }
       currexitstatus = 0;
       return 0; }
       
       if(n_tokens > 2) {
       fprintf(stderr, "cd: wrong number of arguments\n");
       currexitstatus = 1;
       return 1; }
       if(n_tokens == 2) {
       if(chdir(tokens[1]) == -1) {
       fprintf(stderr, "cd: %s\n", strerror(errno));
       chdir(tokens[1]);
       currexitstatus = 1;
       return 1;
       }    	
    }
    currexitstatus =0;
    return 0;
}        

int pwd(int n_tokens, char* tokens[]){
	if(n_tokens > 1) {
	fprintf(stderr, "pwd: wrong number of arguments\n");
	currexitstatus = 1;
	return 1;
	}
	char cwd[PATH_MAX];
	if (getcwd(cwd, sizeof(cwd)) == NULL) {
	fprintf(stderr, "pwd: %s\n", strerror(errno));
        currexitstatus = 1;
        return 1;
	}
	 printf("%s\n", cwd);
	 
	currexitstatus = 0;
	return 0;
	}
	
int exitfunc(int n_tokens, char* tokens[]) {
   if(n_tokens > 2) {
       fprintf(stderr, "exit: too many arguments\n");
       exit(1);
       currexitstatus =1;
       return 1;}
   else if(n_tokens == 2) {
       	exit(atoi(tokens[1]));
       	currexitstatus = 1;
       	return 1;
       	}
       	else{exit(0);
       	currexitstatus = 0;
       	return 0;
       	} 
}

void remredirection(char* tokens[], int* n_tokens, int index) {
    tokens[index] = NULL; //remove token
    if (index + 1 < *n_tokens) {
        tokens[index + 1] = NULL;// remove arg
    }
    *n_tokens = index; 
}

int external(char* tokens[]) {
    int pid = fork();
    if(pid != 0) {
        //parent
        int status;
        waitpid(pid, &status, 0);
        
        if (WIFEXITED(status)) {
            currexitstatus = WEXITSTATUS(status);
            return currexitstatus;
        }
    } else {
        //chidl
        
        
        signal(SIGINT, SIG_DFL);
        for (int i = 0; tokens[i] != NULL; i++) {
          if (strcmp(tokens[i], ">") == 0 && tokens[i + 1] == NULL) {
    		tokens[i] = NULL;
    		   break;
        }
          else if (strcmp(tokens[i], "<") == 0 && tokens[i + 1] != NULL) {
              int infiledesc = open(tokens[i + 1], O_RDONLY);
              
                if (infiledesc == -1) {
                   fprintf(stderr, "%s: %s\n", tokens[i + 1], strerror(errno));
                    exit(EXIT_FAILURE);
                }
                dup2(infiledesc, STDIN_FILENO);
                close(infiledesc);
                remredirection(tokens, &i, i); //remove <
                
                
            }   else if (strcmp(tokens[i], ">") == 0 && tokens[i + 1] != NULL) {
               int outfiledesc = open(tokens[i + 1], O_WRONLY | O_CREAT | O_TRUNC, 0777);
                if (outfiledesc == -1) {
                    fprintf(stderr, "%s: %s\n", tokens[i + 1], strerror(errno));
                    exit(EXIT_FAILURE);
                }
                dup2(outfiledesc, STDOUT_FILENO);
                close(outfiledesc);
                remredirection(tokens, &i, i); //remove >
            }
        }
           if (execvp(tokens[0], tokens) == -1) {
           fprintf(stderr, "%s: %s\n", tokens[0], strerror(errno));
            exit(EXIT_FAILURE);
        }
        exit(currexitstatus);
    }
    return currexitstatus; }

void replacestatus(char* tokens[]) {
char statusbuffer[16];
sprintf(statusbuffer, "%d", currexitstatus);

     for(int i = 0; tokens[i] != NULL; i++) {
        if (strcmp(tokens[i], "$?") == 0) {
            snprintf(tokens[i], sizeof(statusbuffer), "%s", statusbuffer);
        }
    }
}


int main(int argc, char **argv)
{



    signal(SIGINT, SIG_IGN); /* ignore SIGINT=^C */
    bool interactive = isatty(STDIN_FILENO); /* see: man 3 isatty */
    FILE *fp = stdin;

    if (argc == 2) {
        interactive = false;
        fp = fopen(argv[1], "r");
        if (fp == NULL) {
            fprintf(stderr, "%s: %s\n", argv[1], strerror(errno));
            exit(EXIT_FAILURE); /* see: man 3 exit */
        }
    }
    if (argc > 2) {
        fprintf(stderr, "%s: too many arguments\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    char line[1024], linebuf[1024];
    const int max_tokens = 32;
    char *tokens[max_tokens];

    /* loop:
     *   if interactive: print prompt
     *   read line, break if end of file
     *   tokenize it
     *   print it out <-- your logic goes here
     */
    while (true) {
        if (interactive) {
            /* print prompt. flush stdout, since normally the tty driver doesn't
             * do this until it sees '\n'
             */
            printf("sh3650> ");
            fflush(stdout);
        }

        /* see: man 3 fgets (fgets returns NULL on end of file)
         */
        if (!fgets(line, sizeof(line), fp))
            break;

        /* read a line, tokenize it, and print it out
         */
        int n_tokens = parse(line, max_tokens, tokens, linebuf, sizeof(linebuf));

	if(n_tokens > 0){
	
	replacestatus(tokens);
        /* replace the code below with your shell:
         */
       
       if(strcmp("cd", tokens[0]) == 0){
       cd(n_tokens, tokens);
       } else if(strcmp("pwd", tokens[0]) == 0) {
       pwd(n_tokens, tokens);}
       
       else if(strcmp("exit", tokens[0]) == 0) {
	exitfunc(n_tokens, tokens); }
	
	
	else { external(tokens); }
	
}
}

    
     if (interactive)            /* make things pretty */
        printf("\n");           /* try deleting this and then quit with ^D */

}



