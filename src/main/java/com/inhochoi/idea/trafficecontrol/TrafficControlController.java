package com.inhochoi.idea.trafficecontrol;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/ideas/traffic-control")
public class TrafficControlController {

    @GetMapping
    public Map<Integer, String> trafficControl() {
        Map<Long, String> controlMap = Maps.newHashMap();
        LongStream.range(0, 30).forEachOrdered(i -> controlMap.put(i, "A"));
        LongStream.range(30, 100).forEachOrdered(i -> controlMap.put(i, "B"));

        Map<Integer, String> controlResult = Maps.newHashMap();
        Random rand = new Random();
        HashFunction hashFunction = Hashing.murmur3_128(100);

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        for (int i = 0; i < 100; i++) {
            int integer = Math.abs(rand.nextInt());
            HashCode hashCode = hashFunction.hashLong(integer);
            long option = Math.abs(hashCode.asLong() % 100);
            String type = controlMap.get(option);

            if (type.equals("A")) {
                controlResult.put(Integer.valueOf(i), "A");
            }
            if (type.equals("B")) {
                controlResult.put(Integer.valueOf(i), "B");
            }
        }

        return controlResult;
    }
}
