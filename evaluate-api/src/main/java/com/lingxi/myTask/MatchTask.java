package com.lingxi.myTask;

import com.lingxi.service.GMatchesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchTask {

    private final GMatchesService matchesService;

    //fixedDelay: 指定两次任务执行之间的固定延迟时间（以毫秒为单位），
    //initialDelay: 指定首次执行任务的延迟时间（以毫秒为单位），即启动后首次执行任务前的等待时间。
    //cron: 使用Cron表达式来定义更复杂的调度规则，如按特定时间、日期或周期执行任务。
    //zone: 指定任务的时区，默认为系统默认时区。可以使用java.util.TimeZone中的时区ID来设置。

    /**
     * 定时开启,结束赛程的
     */
//    @Scheduled(initialDelay = 5000, fixedRate = 86400000, zone = "Asia/Shanghai")
//    @Transactional
//    public void optionMatch() {
//        LocalDateTime currentTime = LocalDateTime.now();
//        List<GMatches> matchesList = matchesService.list();
//        if (matchesList != null) {
//            matchesList.stream()
//                    .filter(match -> currentTime.isEqual(match.getStartTime())
//                            || currentTime.isAfter(match.getStartTime()))
//                    .forEach(match -> {
//                        if (currentTime.isEqual(match.getStartTime())
//                                || (currentTime.isAfter(match.getStartTime())
//                                && currentTime.isBefore(match.getOverTime()))) {
//                            match.setStatus(SystemEnum.M_ONGOING.getValue());
//                            matchesService.updateById(match);
//                        } else if (currentTime.isEqual(match.getOverTime())
//                                || currentTime.isAfter(match.getOverTime())) {
//                            match.setStatus(SystemEnum.M_OVER.getValue());
//                            matchesService.updateById(match);
//                        }
//                    });
//        }
//    }


}
