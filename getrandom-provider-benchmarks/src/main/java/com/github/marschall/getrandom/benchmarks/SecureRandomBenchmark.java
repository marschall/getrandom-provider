package com.github.marschall.getrandom.benchmarks;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.github.marschall.getrandom.GetrandomProvider;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class SecureRandomBenchmark {

  @Param({GetrandomProvider.GETURANDOM, "NativePRNGNonBlocking"})
  public String algorithm;

  private SecureRandom secureRandom;

  @Setup
  public void setup() throws NoSuchAlgorithmException {
    // in theory the ServiceLoader should find this but doesn't
    Security.addProvider(new GetrandomProvider());
    this.secureRandom = SecureRandom.getInstance(this.algorithm);
    this.secureRandom.nextBoolean(); // seed
  }

  @Benchmark
  public boolean nextBoolean() {
    return this.secureRandom.nextBoolean();
  }

  @Benchmark
  public int nextInt() {
    return this.secureRandom.nextInt();
  }

  @Benchmark
  public long nextLong() {
    return this.secureRandom.nextLong();
  }

}
