package com.github.marschall.getrandom.benchmarks;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.github.marschall.getrandom.GetrandomProvider;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class GlobalSecureRandomBenchmark {

  private SecureRandom getrandom;

  private SecureRandom nativePRNGNonBlocking;

  @Setup
  public void setup() throws NoSuchAlgorithmException {
    // in theory the ServiceLoader should find this but doesn't
    Security.addProvider(new GetrandomProvider());
    this.getrandom = SecureRandom.getInstance(GetrandomProvider.GETRANDOM);
    this.getrandom.nextBoolean(); // seed
    this.nativePRNGNonBlocking = SecureRandom.getInstance("NativePRNGNonBlocking");
    this.nativePRNGNonBlocking.nextBoolean(); // seed
  }

  @Benchmark
  public int getrandom_1byte() {
    return this.getrandom.nextInt();
  }

  @Benchmark
  public int getrandom_4byte() {
    return this.getrandom.nextInt();
  }

  @Benchmark
  public long getrandom_8byte() {
    return this.getrandom.nextLong();
  }

  @Benchmark
  public int nativeprng_1byte() {
    return this.nativePRNGNonBlocking.nextInt();
  }

  @Benchmark
  public int nativeprng_4byte() {
    return this.nativePRNGNonBlocking.nextInt();
  }

  @Benchmark
  public long nativeprng_8byte() {
    return this.nativePRNGNonBlocking.nextLong();
  }

}
