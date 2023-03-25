package io.spring.dataflow.sample.usagecostlogger;

//import io.spring.dataflow.sample.usagecostprocessor.UsageCostProcessor;
//import io.spring.dataflow.sample.usagedetailsender.UsageDetail;
//import io.spring.dataflow.sample.usagedetailsender.UsageDetailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;

//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Supplier;

@ExtendWith(OutputCaptureExtension.class)
public class ShortcutTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testShortcuts() {

//        UsageDetailSender uds = new UsageDetailSender();
//        Supplier<UsageDetail> usageDetailSupplier = uds.sendEvents();
//        UsageDetail usageDetail = usageDetailSupplier.get();
//
//        UsageCostProcessor ucp = new UsageCostProcessor();
//        Function<io.spring.dataflow.sample.usagecostprocessor.UsageDetail, io.spring.dataflow.sample.usagecostprocessor.UsageCostDetail>
//                processUsageCost = ucp.processUsageCost();
//
//        io.spring.dataflow.sample.usagecostprocessor.UsageDetail processorUsageDetail =
//                new io.spring.dataflow.sample.usagecostprocessor.UsageDetail();
//
//        processorUsageDetail.setUserId(usageDetail.getUserId());
//        processorUsageDetail.setData(usageDetail.getData());
//        processorUsageDetail.setDuration(usageDetail.getDuration());
//
//        io.spring.dataflow.sample.usagecostprocessor.UsageCostDetail usageCostDetail = processUsageCost.apply(processorUsageDetail);
//
//        io.spring.dataflow.sample.usagecostlogger.UsageCostDetail logUsageCostDetail =
//                new io.spring.dataflow.sample.usagecostlogger.UsageCostDetail();
//
//        logUsageCostDetail.setUserId(usageCostDetail.getUserId());
//        logUsageCostDetail.setDataCost(usageCostDetail.getDataCost());
//        logUsageCostDetail.setCallCost(usageCostDetail.getCallCost());
//
//        UsageCostLogger ucl = new UsageCostLogger();
//        Consumer<UsageCostDetail> process = ucl.process();
//
//        process.accept(logUsageCostDetail);
//
//        System.out.println("%%%%%%%%%%%%%   And we're done..");
    }

}

