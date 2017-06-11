<?php

namespace MirrorApiBundle\Controller;


use MirrorApiBundle\Entity\User;
use MirrorApiBundle\Form\UserType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;

class UserController extends Controller
{
    use ControllerTrait;

    /**
     * @Rest\View(serializerGroups={"user"})
     * @Rest\Get("/user/{user_id}")
     * @return JsonResponse
     */
    public function getUserAction(Request $request)
    {
        $repository = $this->getDoctrine()->getRepository('MirrorApiBundle:User');

        $user = $repository->getUserAndModules($request->get("user_id"));

        if (empty($user)) {
            $this->userNotFound();
        }

        //TODO ajouter les href

        return $user;
        //return $this->render('MirrorApiBundle:User:index.html.twig', []);
    }

    /**
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     * @Rest\Post("/user")
     * @return JsonResponse
     */
    public function postUserAction(Request $request) {
        $user = new User();

        $form = $this->createForm(UserType::class, $user);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            return $user;
        } else {
            return $form;
        }
    }
}
