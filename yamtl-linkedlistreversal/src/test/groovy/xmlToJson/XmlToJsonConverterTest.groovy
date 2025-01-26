package xmlToJson

import static org.junit.Assert.assertTrue

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.jupiter.api.Test

import yamtl.core.YAMTLModule
import yamtl.groovy.YAMTLGroovyExtensions_dynamicEMF
import yamtl.utils.EMFComparator

class XmlToJsonConverterTest extends YAMTLModule {
    final BASE_PATH = 'model'
    
    @Test
    def void testXmlToJsonConversion() {
        // model transformation execution example
        def xmlInputRes = YAMTLModule.preloadMetamodel(BASE_PATH + '/xmlinput.ecore') as Resource
        def jsonOutputRes = YAMTLModule.preloadMetamodel(BASE_PATH + '/jsonoutput.ecore') as Resource
        def xform = new XmlToJsonConverter(xmlInputRes.contents[0] as EPackage, jsonOutputRes.contents[0] as EPackage)
        xform.loadInputModels(['in': BASE_PATH + '/example.xml'])
        xform.execute()
        xform.saveOutputModels(['out': BASE_PATH + '/example.json'])

//        // test assertion
//        def actualModel = xform.getOutputModel('out')
//        EMFComparator comparator = new EMFComparator()
//        def expectedResource = xform.loadModel(BASE_PATH + '/expected.json', false)
//        assertTrue(comparator.equals(expectedResource.getContents(), actualModel.getContents()))
    }
}