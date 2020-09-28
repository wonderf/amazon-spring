package app.services;

import app.entity.TaskResult;
import app.utils.ExcelHelper;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    public List<ByteArrayInputStream> load(List<TaskResult> resultList,int limit) {
        List<ByteArrayInputStream> streams = new ArrayList<>();
        List<List<TaskResult>> parts = ListUtils.partition(resultList, limit);
        for(int i=0;i<parts.size();i++){
            streams.add(ExcelHelper.export(parts.get(i)));
        }
        return streams;
    }
}
