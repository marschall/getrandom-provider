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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class SecureRandomBenchmark {

  @Param({GetrandomProvider.GETURANDOM, "NativePRNGNonBlocking"})
  public String algorithm;

  private SecureRandom secureRandom;

  private byte[] bytes;

  @Setup
  public void setup() throws NoSuchAlgorithmException {
    Security.addProvider(new GetrandomProvider());
    this.secureRandom = SecureRandom.getInstance(this.algorithm);
    this.secureRandom.nextBoolean(); // seed
    this.bytes = new byte[256];
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

  @Benchmark
  public byte[] nextBytes256() {
    this.secureRandom.nextBytes(this.bytes);
    return this.bytes;
  }

}
