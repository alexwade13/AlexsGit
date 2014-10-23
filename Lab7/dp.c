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
	napping(2);
	printf("Philosopher %d is hungry\n", id);
	pthread_mutex_lock(chopsticks[id]);
	pthread_mutex_lock(chopsticks[(id+1)%5]);
	printf("Philosopher %d is starting to eat\n", id);
	napping(1);
	printf("Philosopher %d is done eating\n", id);
	pthread_mutex_unlock(chopsticks[id]);
	pthread_mutex_unlock(chopsticks[(id+1)%5]);
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

