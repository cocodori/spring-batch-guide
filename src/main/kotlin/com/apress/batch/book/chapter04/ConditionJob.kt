package com.apress.batch.book.chapter04

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.flow.JobExecutionDecider
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConditionJob(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun job(): Job =
        jobBuilderFactory["conditionalJob"]
            .start(firstStep())
            .next(decider())
            .from(decider())
            .on("FAILED").to(failureStep())
            .from(decider()).on("*").to(successStep())
            .end()
            .build()

    @Bean
    fun decider(): JobExecutionDecider = RandomDecider()

    @Bean
    fun firstStep(): Step =
        stepBuilderFactory["firstStep"]
            .tasklet(passTasklet())
            .build()

    @Bean
    fun successStep(): Step =
        stepBuilderFactory["successStep"]
            .tasklet(successTasklet())
            .build()

    @Bean
    fun failureStep(): Step =
        stepBuilderFactory["failureStep"]
            .tasklet(failTasklet())
            .build()

    @Bean
    fun passTasklet(): Tasklet =
        Tasklet { contribution, chunkContext ->
            RepeatStatus.FINISHED
        }

    @Bean
    fun successTasklet(): Tasklet =
        Tasklet { contribution, chunkContext ->
            RepeatStatus.FINISHED
        }

    @Bean
    fun failTasklet(): Tasklet =
        Tasklet { contribution, chunkContext ->
            RepeatStatus.FINISHED
        }
}