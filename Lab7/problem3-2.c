#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t * chopsticks[5]; 


void * Philosopher (void *ID) {
     int id = (int) ID;
     while(1) {
	printf("Philosopher %d is thinking\n", id);
	fflush(stdout);
	napping(2);
	printf("Philosopher %d is hungry\n", id);
	fflush(stdout);
	if (id < ((id+1)%5) ){
		pthread_mutex_lock(chopsticks[id]);
		printf("Philosopher %d is picking up chopstick %d\n",id,id);
		fflush(stdout);
		napping(5);
		printf("Philosopher %d is trying to grab next chopstick\n", id);
		fflush(stdout);
		pthread_mutex_lock(chopsticks[(id+1)%5]);
		printf("Philosopher %d is picking up chopstick %d\n",id,(id+1)%5);
		fflush(stdout);
	} else {
	
		pthread_mutex_lock(chopsticks[(id+1)%5]);
		printf("Philosopher %d is picking up chopstick %d\n",id,(id+1)%5);
		fflush(stdout);

		napping(5);
		printf("Philosopher %d is trying to grab next chopstick\n", id);
		fflush(stdout);

		pthread_mutex_lock(chopsticks[id]);
		printf("Philosopher %d is picking up chopstick %d\n",id,id);
		fflush(stdout);
	}
	printf("Philosopher %d is starting to eat\n", id);
	fflush(stdout);
	napping(1);
	printf("Philosopher %d is done eating\n", id);
	fflush(stdout);
	pthread_mutex_unlock(chopsticks[id]);
	printf("Philosopher %d is putting down chopstick %d\n",id,id);
	fflush(stdout);
	pthread_mutex_unlock(chopsticks[(id+1)%5]);
	printf("Philosopher %d is putting down chopstick %d\n",id,(id+1)%5);
	fflush(stdout);
     }
}
int counter = 1;
int napping(int x) {
	
	int rand_sleep = (((double)x * (double)rand_r(&counter)/(double)RAND_MAX) * 1000000);
	usleep(rand_sleep);	
	counter ++;
}

int main (int argc, char *argv[]) {
     int num_phil = 0;
     int i = 0;
     for(i = 0; i < 5; i ++) {
	chopsticks[i] = malloc(sizeof(pthread_mutex_t));
	pthread_mutex_init(chopsticks[i], NULL);    
     }
     while(1) {
	if (num_phil > 4){
	} else {
	pthread_t buff;
	pthread_create(&buff, NULL, Philosopher, (void *)num_phil);
	num_phil ++;
	}
     }
}

