package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.factory.SpiExtensionFactory;
import com.lv.distributed.support.Chooser;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ProjectName: Demo
 * @Author: lvminghui
 * @Description: SpiDemo
 * @Date: 2022/9/16 17:39
 * @Version: 1.0
 */
public class Demo {

    public static void main(String[] args) {
//        FaultTolerantStrategyChooser<FaultTolerantStrategy> chooser = new FaultTolerantStrategyChooser<FaultTolerantStrategy>(new SpiExtensionFactory());
//        chooser.choose("default");
//        testEurekaClusterBalance();
        String ip = "192.168.1.1";
        int hashCode = ip.hashCode();
        System.out.println("hashCode=="+hashCode);
        Random random = new Random(hashCode);
        for (int i = 0; i < 3; i++) {
            int pos = random.nextInt(3 - i);
            System.out.println("pos=="+pos+",index=="+i);
        }

    }

    private static void testEurekaClusterBalance() {
        int ipLoopSize = 65000;
        String ipFormat = "192.168.%s.%s";
        TreeMap<String, Integer> ipMap = new TreeMap<>();
        int netIndex = 0;
        int lastIndex = 0;
        for (int i = 0; i < ipLoopSize; i++) {
            if (lastIndex == 256) {
                netIndex += 1;
                lastIndex = 0;
            }

            String ip = String.format(ipFormat, netIndex, lastIndex);
            randomize(ip, ipMap);
            System.out.println("IP: " + ip);
            lastIndex += 1;
        }

        printIpResult(ipMap, ipLoopSize);
    }

    /**
     * 模拟指定ip地址获取对应注册中心负载
     */
    private static void randomize(String eurekaClientIp, TreeMap<String, Integer> ipMap) {
        List<String> eurekaServerUrlList = new ArrayList<>();
        eurekaServerUrlList.add("http://peer1:8080/eureka/");
        eurekaServerUrlList.add("http://peer2:8080/eureka/");
        eurekaServerUrlList.add("http://peer3:8080/eureka/");

        List<String> randomList = new ArrayList<>(eurekaServerUrlList);
        Random random = new Random(eurekaClientIp.hashCode());
        int last = randomList.size() - 1;
        for (int i = 0; i < last; i++) {
            int pos = random.nextInt(randomList.size() - i);
            if (pos != i) {
                Collections.swap(randomList, i, pos);
            }
        }

        for (String eurekaHost : randomList) {
            int ipCount = ipMap.get(eurekaHost) == null ? 0 : ipMap.get(eurekaHost);
            ipMap.put(eurekaHost, ipCount + 1);
            break;
        }
    }

    private static void printIpResult(TreeMap<String, Integer> ipMap, int totalCount) {
        for (Map.Entry<String, Integer> entry : ipMap.entrySet()) {
            Integer count = entry.getValue();
            BigDecimal rate = new BigDecimal(count).divide(new BigDecimal(totalCount), 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(entry.getKey() + ":" + count + ":" + rate.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
        }
    }
}
