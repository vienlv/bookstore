package org.exoplatform.bookstore.chromattic.entity;
import org.chromattic.apt.Invoker;
import org.chromattic.apt.Instrumented;
public class CategoryRootEntity_Chromattic extends CategoryRootEntity implements Instrumented {
public final org.chromattic.spi.instrument.MethodHandler handler;
public CategoryRootEntity_Chromattic(org.chromattic.spi.instrument.MethodHandler handler) {
this.handler = handler;
}
private static final Invoker method_0 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"getCategories");
public final java.util.Map getCategories() {
try {
return (java.util.Map)handler.invoke(this,method_0.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_1 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"setBookstore",org.exoplatform.bookstore.chromattic.entity.BookstoreEntity.class);
public final void setBookstore(org.exoplatform.bookstore.chromattic.entity.BookstoreEntity arg_0) {
try {
handler.invoke(this,method_1.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_2 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"createCategory");
public final org.exoplatform.bookstore.chromattic.entity.CategoryEntity createCategory() {
try {
return (org.exoplatform.bookstore.chromattic.entity.CategoryEntity)handler.invoke(this,method_2.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_3 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"getBookstore");
public final org.exoplatform.bookstore.chromattic.entity.BookstoreEntity getBookstore() {
try {
return (org.exoplatform.bookstore.chromattic.entity.BookstoreEntity)handler.invoke(this,method_3.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_4 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"getName");
public final java.lang.String getName() {
try {
return (java.lang.String)handler.invoke(this,method_4.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_5 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"getPath");
public final java.lang.String getPath() {
try {
return (java.lang.String)handler.invoke(this,method_5.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_6 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class,"getId");
public final java.lang.String getId() {
try {
return (java.lang.String)handler.invoke(this,method_6.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
}
