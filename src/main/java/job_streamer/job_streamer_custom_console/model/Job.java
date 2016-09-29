package job_streamer.job_streamer_custom_console.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job {

    private String name;
    private String lastExitStatus;
    private Date lastExecutionEndTime;
    // TODO: 他にも必要なプロパティがあれば追加
}
