module com.github.marschall.getrandom {
  provides java.security.Provider with com.github.marschall.getrandom.GetrandomProvider;
  requires java.security.jgss;
}
