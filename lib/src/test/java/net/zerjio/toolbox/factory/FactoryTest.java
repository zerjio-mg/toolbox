package net.zerjio.toolbox.factory;

import net.zerjio.toolbox.factory.dependencies.*;
import net.zerjio.toolbox.factory.classes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactoryTest {

   @Test
   public void givenClassWithoutDependencies_thenIsSuccessfullyCreated() {

      Factory factory = new BasicFactory(new SimpleDependencies());

      Class_C instanceOne = factory.instanceOf(Class_C.class);
      Class_C instanceTwo = factory.instanceOf(Class_C.class);

      assertEquals(1, factory.size());
      assertEquals(instanceOne, instanceTwo);
      assertEquals("C.1", instanceOne.call());
   }

   @Test
   public void givenClassWithOneDependency_thenIsSuccessfullyCreated() {

      Factory factory = new BasicFactory(new SimpleDependencies());

      Class_B instanceOne = factory.instanceOf(Class_B.class);
      Class_B instanceTwo = factory.instanceOf(Class_B.class);

      assertEquals(2, factory.size());
      assertEquals(instanceOne, instanceTwo);
      assertEquals("B.2(C.1)", instanceOne.call());
   }

   @Test
   public void givenClassWithTwoDependenciesInTwoLevels_thenIsSuccessfullyInjected() {

      BasicFactory dependenciesInjector = new BasicFactory(new SimpleDependencies());

      Class_A instanceOne = dependenciesInjector.instanceOf(Class_A.class);
      Class_A instanceTwo = dependenciesInjector.instanceOf(Class_A.class);

      assertEquals(3, dependenciesInjector.size());
      assertEquals(instanceOne, instanceTwo);
      assertEquals("A.3(B.2(C.1))", instanceOne.call());
   }

   @Test
   public void givenClassWithTwoDependencies_thenIsSuccessfullyInjected() {

      BasicFactory dependenciesInjector = new BasicFactory(new ComplexDependencies());

      Class_F instanceOne = dependenciesInjector.instanceOf(Class_F.class);
      Class_F instanceTwo = dependenciesInjector.instanceOf(Class_F.class);

      assertEquals(3, dependenciesInjector.size());
      assertEquals(instanceOne, instanceTwo);
      assertEquals("F.3(G.1,H.2)", instanceOne.call());
   }

   @Test
   public void givenClassWithThreeDependenciesInTwoLevels_thenIsSuccessfullyInjected() {

      BasicFactory dependenciesInjector = new BasicFactory(new ComplexDependencies());

      Class_E instanceOne = dependenciesInjector.instanceOf(Class_E.class);
      Class_E instanceTwo = dependenciesInjector.instanceOf(Class_E.class);

      assertEquals(4, dependenciesInjector.size());
      assertEquals(instanceOne, instanceTwo);
      assertEquals("E.4(F.3(G.1,H.2))", instanceOne.call());
   }

   @Test
   public void givenClassWithFourDependenciesInThreeLevels_thenIsSuccessfullyInjected() {

      BasicFactory dependenciesInjector = new BasicFactory(new ComplexDependencies());

      Class_D instanceOne = dependenciesInjector.instanceOf(Class_D.class);
      Class_D instanceTwo = dependenciesInjector.instanceOf(Class_D.class);

      assertEquals(5, dependenciesInjector.size());
      assertEquals(instanceOne, instanceTwo);
      assertEquals("D.5(E.4(F.3(G.1,H.2)),F.3(G.1,H.2))", instanceOne.call());
   }

   @Test
   public void givenSingletonDependencies_thenIsSuccessfullyInjected() {

      BasicFactory dependenciesInjector = new BasicFactory(new PrototypeDependency());

      Class_A instanceOne = dependenciesInjector.instanceOf(Class_A.class);
      Class_A instanceTwo = dependenciesInjector.instanceOf(Class_A.class);

      assertEquals(1, dependenciesInjector.size());
      assertNotEquals(instanceOne, instanceTwo);
      assertEquals("A.3(B.2(C.1))", instanceOne.call());
      assertEquals("A.5(B.4(C.1))", instanceTwo.call());
   }

   @Test
   public void givenTwoFactoryDependencies_thenIsSuccessfullyInjected() {

      BasicFactory dependenciesInjector = new BasicFactory(
         new SimpleDependencies(),
         new ComplexDependencies()
      );

      Class_A instanceA1 = dependenciesInjector.instanceOf(Class_A.class);
      Class_A instanceA2 = dependenciesInjector.instanceOf(Class_A.class);
      Class_D instanceD1 = dependenciesInjector.instanceOf(Class_D.class);
      Class_D instanceD2 = dependenciesInjector.instanceOf(Class_D.class);

      assertEquals(8, dependenciesInjector.size());
      assertEquals(instanceA1, instanceA2);
      assertEquals(instanceD1, instanceD2);
      assertEquals("A.3(B.2(C.1))", instanceA1.call());
      assertEquals("D.5(E.4(F.3(G.1,H.2)),F.3(G.1,H.2))", instanceD1.call());
   }

   @Test
   public void givenClassNotDeclared_thenErrorIsThrown() {

      BasicFactory dependenciesInjector = new BasicFactory(new NotFoundDependency());

      FactoryException exception = assertThrows(
          FactoryException.class,
          () -> dependenciesInjector.instanceOf(Class_C.class)
      );

      assertEquals("Instance of 'net.zerjio.toolbox.factory.classes.Class_C' not found", exception.getMessage());
   }

   @Test
   public void givenClassWithNotDeclaredDependency_thenErrorIsThrown() {

      BasicFactory dependenciesInjector = new BasicFactory(new NotFoundDependency());

      assertThrows(
          FactoryException.class,
          () -> dependenciesInjector.instanceOf(Class_B.class)
      );
   }

   @Test
   public void givenClassWithCyclicDependency_thenErrorIsThrown() {

      BasicFactory dependenciesInjector = new BasicFactory(new CyclicDependency());

      assertThrows(
          FactoryException.class,
          () -> dependenciesInjector.instanceOf(Class_I.class)
      );
   }

   @Test
   public void givenTwoDependenciesWithSameReturnType_thenErrorIsThrown() {

      assertThrows(
          FactoryException.class,
          () -> new BasicFactory(new RepeatedDependencies())
      );
   }

}
